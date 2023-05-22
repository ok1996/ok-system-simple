package cn.iosd.starter.datasource.config;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.net.URI;
import java.net.URISyntaxException;
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

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        if (!RUN) {
            String autoCreate = getPropertyValue(applicationContext, "simple.datasource.autoCreateDatabase");
            if (StringUtils.isBlank(autoCreate) || Boolean.parseBoolean(autoCreate)) {
                String url = getPropertyValue(applicationContext, "spring.datasource.dynamic.datasource.master.url");
                String username = getPropertyValue(applicationContext, "spring.datasource.dynamic.datasource.master.username");
                String password = getPropertyValue(applicationContext, "spring.datasource.dynamic.datasource.master.password");
                if (StringUtils.isAnyBlank(url, username, password)) {
                    return;
                }
                initDatabase(url, username, password);
            }
            RUN = true;
        }
    }

    /**
     * 获取指定配置属性的值
     *
     * @param applicationContext 应用程序上下文
     * @param propertyName       配置属性名
     * @return 配置属性的值
     */
    private String getPropertyValue(ConfigurableApplicationContext applicationContext, String propertyName) {
        return applicationContext.getEnvironment().getProperty(propertyName);
    }

    private void initDatabase(String url, String username, String password) {
        String database = parseDatabaseName(url);
        url = removeDatabaseName(url, database);
        log.info("AutoCreateDatabase：初始化数据库,数据库名:{},连接地址:{} ", database, url);

        String mysql = ":mysql:";
        String createDataBaseSql = "";
        if (url.contains(mysql)) {
            createDataBaseSql = "create database if not exists `" + database + "` DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_general_ci";
        }
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stat = conn.createStatement()) {
            stat.executeUpdate(createDataBaseSql);
        } catch (SQLException e) {
            log.error("创建数据库失败: {}", e.getMessage(), e);
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
        if (StringUtils.isBlank(jdbcUrl)) {
            throw new IllegalArgumentException("jdbcUrl is null or empty");
        }
        try {
            URI uri = new URI(jdbcUrl.substring(5));
            String path = uri.getPath();
            String databaseName = path.substring(1);
            if (StringUtils.isBlank(databaseName)) {
                throw new IllegalArgumentException("can not parse database name from jdbcUrl: " + jdbcUrl);
            }
            return databaseName;
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("can not parse database name from jdbcUrl: " + jdbcUrl + ". Invalid URI syntax", e);
        }
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
