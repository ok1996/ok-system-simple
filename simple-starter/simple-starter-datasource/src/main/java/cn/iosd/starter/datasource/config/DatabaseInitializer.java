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

    private static final String AUTO_CREATE_PROPERTY = "simple.datasource.autoCreateDatabase";
    private static final String URL_PROPERTY = "spring.datasource.dynamic.datasource.master.url";
    private static final String USERNAME_PROPERTY = "spring.datasource.dynamic.datasource.master.username";
    private static final String PASSWORD_PROPERTY = "spring.datasource.dynamic.datasource.master.password";

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        if (shouldAutoCreateDatabase(applicationContext)) {
            String url = getPropertyValue(applicationContext, URL_PROPERTY);
            String username = getPropertyValue(applicationContext, USERNAME_PROPERTY);
            String password = getPropertyValue(applicationContext, PASSWORD_PROPERTY);
            if (StringUtils.isAnyBlank(url, username, password)) {
                return;
            }
            initDatabase(url, username, password);
        }
    }

    private boolean shouldAutoCreateDatabase(ConfigurableApplicationContext applicationContext) {
        String autoCreate = getPropertyValue(applicationContext, AUTO_CREATE_PROPERTY);
        return StringUtils.isBlank(autoCreate) || Boolean.parseBoolean(autoCreate);
    }

    private void initDatabase(String url, String username, String password) {
        String databaseName = parseDatabaseName(url);
        String urlSimplify = removeDatabaseName(url, databaseName);

        String mysql = ":mysql:";
        if (!urlSimplify.contains(mysql)) {
            return;
        }

        log.info("Initializing database, Database Name: {}, Connection URL: {}", databaseName, urlSimplify);
        String createDataBaseSql = "create database if not exists `" + databaseName + "` DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_general_ci";

        try (Connection conn = DriverManager.getConnection(urlSimplify, username, password);
             Statement stat = conn.createStatement()) {
            stat.executeUpdate(createDataBaseSql);
        } catch (SQLException e) {
            log.error("Failed to create database: {}", e.getMessage(), e);
        }
    }

    private String getPropertyValue(ConfigurableApplicationContext applicationContext, String propertyName) {
        return applicationContext.getEnvironment().getProperty(propertyName);
    }

    private String parseDatabaseName(String jdbcUrl) {
        if (StringUtils.isBlank(jdbcUrl)) {
            throw new IllegalArgumentException("jdbcUrl is null or empty");
        }

        try {
            URI uri = new URI(jdbcUrl.substring(5));
            String path = uri.getPath();
            String databaseName = path.substring(1);

            if (StringUtils.isBlank(databaseName)) {
                throw new IllegalArgumentException("Cannot parse database name from jdbcUrl: " + jdbcUrl);
            }

            return databaseName;
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Cannot parse database name from jdbcUrl: " + jdbcUrl + ". Invalid URI syntax", e);
        }
    }

    private String removeDatabaseName(String jdbcUrl, String databaseName) {
        return jdbcUrl.replace("/" + databaseName, "");
    }
}