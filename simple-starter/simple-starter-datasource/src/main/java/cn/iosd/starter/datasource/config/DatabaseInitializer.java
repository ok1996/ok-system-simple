package cn.iosd.starter.datasource.config;


import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Db;
import cn.hutool.db.DbUtil;
import cn.hutool.db.StatementUtil;
import cn.hutool.db.ds.simple.SimpleDataSource;
import cn.hutool.db.sql.SqlExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * 数据库不存在时创建数据库
 *
 * @author ok1996
 */
@Slf4j
public class DatabaseInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static volatile boolean RUN = false;
    private static final String STR_FALSE = "false";

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        if (!RUN) {
            String autoCreate = applicationContext.getEnvironment().getProperty("simple.datasource.autoCreateDatabase");
            if (!STR_FALSE.equalsIgnoreCase(autoCreate)) {
                String url = applicationContext.getEnvironment().getProperty("spring.datasource.dynamic.datasource.master.url");
                String username = applicationContext.getEnvironment().getProperty("spring.datasource.dynamic.datasource.master.username");
                String password = applicationContext.getEnvironment().getProperty("spring.datasource.dynamic.datasource.master.password");
                if (StrUtil.hasBlank(url, username, password)) {
                    return;
                }
                try {
                    log.info("初始化数据库: 数据库若不存在则自动创建数据库");
                    initDatabase(url, username, password);
                } catch (Exception e) {
                    log.info("数据库初始化失败");
                    log.error(e.getMessage(), e);
                }
            }
            RUN = true;
        }
    }

    private void initDatabase(String url, String username, String password) {
        String database = parseDatabaseName(url);
        log.info("数据库名:{}", database);
        url = removeDatabaseName(url, database);
        log.info("jdbcUrl:{}", url);

        DataSource ds = new SimpleDataSource(url, username, password);
        Db db = DbUtil.use(ds);
        Connection conn = null;

        String mysql = ":mysql:";
        String createDataBaseSql = null;
        if (url.contains(mysql)) {
            createDataBaseSql = StrUtil.format(
                    "create database if not exists `{}` DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_general_ci", database);
        }

        try {
            conn = ds.getConnection();
            PreparedStatement preparedStatement = StatementUtil.prepareStatement(conn, createDataBaseSql, new Object[0]);
            SqlExecutor.execute(preparedStatement, new Object[0]);
        } catch (Exception e) {
            log.error("创建数据库失败:{}", e.getMessage(), e);
        } finally {
            db.closeConnection(conn);
        }

    }

    /**
     * 根据 jdbcUrl 解析出数据库名
     * 示例: jdbc:mysql://127.0.0.1:3306/pure?useUnicode
     * 从提取到 pure 作为数据库名
     *
     * @param jdbcUrl jdbcUrl
     * @return 数据库名
     */
    String parseDatabaseName(String jdbcUrl) {
        int index = jdbcUrl.indexOf("://");
        if (index == -1) {
            throw new IllegalArgumentException("can not parse database name from jdbcUrl:" + jdbcUrl);
        }

        int slashIndex = jdbcUrl.indexOf("/", index + 3);
        slashIndex++;
        if (slashIndex == 0 || slashIndex == jdbcUrl.length()) {
            throw new IllegalArgumentException("can not parse database name from jdbcUrl:" + jdbcUrl);
        }

        int questionMarkIndex = jdbcUrl.indexOf("?", slashIndex);
        String databaseName = questionMarkIndex == -1
                ? jdbcUrl.substring(slashIndex)
                : jdbcUrl.substring(slashIndex, questionMarkIndex);

        if (StrUtil.isBlank(databaseName)) {
            throw new IllegalArgumentException("can not parse database name from jdbcUrl:" + jdbcUrl);
        }
        return databaseName;
    }

    /**
     * 删除 jdbcUrl 的数据库名
     *
     * @param jdbcUrl      jdbcUrl
     * @param databaseName 数据库名
     * @return jdbcUrl
     */
    String removeDatabaseName(String jdbcUrl, String databaseName) {
        return jdbcUrl.replace("/" + databaseName, "");
    }

}
