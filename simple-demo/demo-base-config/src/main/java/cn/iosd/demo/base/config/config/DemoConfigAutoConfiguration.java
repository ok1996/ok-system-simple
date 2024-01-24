package cn.iosd.demo.base.config.config;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 配置Flyway
 *
 * @author ok1996
 */
@Configuration
public class DemoConfigAutoConfiguration {

    @Bean
    public Flyway configDemoFlyway(DataSource dataSource) {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/mysql/demo/config")
                .baselineOnMigrate(true)
                .table("demo_config_flyway_schema_history")
                .load();
        flyway.migrate();
        return flyway;
    }

}
