package cn.iosd.demo.base.param.config;

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
public class DemoParamAutoConfiguration {

    @Bean
    public Flyway paramFlyway(DataSource dataSource) {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/mysql/demo/param")
                .baselineOnMigrate(true)
                .table("demo_param_flyway_schema_history")
                .load();
        flyway.migrate();
        return flyway;
    }

}
