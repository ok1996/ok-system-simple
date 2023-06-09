package cn.iosd.base.param.config;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author ok1996
 */
@Configuration
public class ParamFlywayConfig {

    @Bean
    public Flyway paramFlyway(DataSource dataSource) {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/mysql/param")
                .baselineOnMigrate(true)
                .table("param_flyway_schema_history")
                .load();
        flyway.migrate();
        return flyway;
    }
}