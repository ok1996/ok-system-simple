package cn.iosd.demo.datasource.config;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author ok1996
 */
@Configuration
public class DatasourceFlywayConfig {

    @Bean
    public Flyway firstFlyway(DataSource dataSource) {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/mysql/ds")
                .baselineOnMigrate(true)
                .table("first_flyway_schema_history")
                .load();
        flyway.migrate();
        return flyway;
    }
}
