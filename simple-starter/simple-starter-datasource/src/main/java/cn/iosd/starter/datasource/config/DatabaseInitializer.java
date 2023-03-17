package cn.iosd.starter.datasource.config;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 数据库不存在时创建数据库
 *
 * @author ok1996
 */
public class DatabaseInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static final Logger log = LoggerFactory.getLogger(DatabaseInitializer.class);

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
                if (StringUtils.isAnyBlank(url, username, password)) {
                    return;
                }
                try {
                    initDatabase(url, username, password);
                } catch (Exception e) {
                    log.error("数据库初始化失败", e.getMessage(), e);
                }
            }
            RUN = true;
        }
    }

    private void initDatabase(String url, String username, String password) throws SQLException {
        String database = parseDatabaseName(url);
        url = removeDatabaseName(url, database);
        log.info("AutoCreateDatabase：初始化数据库,数据库名:{},连接地址:{} ", database, url);

        String mysql = ":mysql:";
        StringBuffer createDataBaseSql = new StringBuffer();
        if (url.contains(mysql)) {
            createDataBaseSql.append("create database if not exists `")
                    .append(database)
                    .append("` DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_general_ci");
        }

        Connection conn = DriverManager.getConnection(url, username, password);
        Statement stat = conn.createStatement();

        try {
            stat.executeUpdate(createDataBaseSql.toString());
        } catch (Exception e) {
            log.error("创建数据库失败:{}", e.getMessage(), e);
        } finally {
            stat.close();
            conn.close();
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

        if (StringUtils.isBlank(databaseName)) {
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
