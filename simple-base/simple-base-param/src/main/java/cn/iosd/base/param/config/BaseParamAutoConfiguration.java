package cn.iosd.base.param.config;

import org.flywaydb.core.Flyway;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 配置MapperScan、Flyway、ComponentScan以及是否启用
 *
 * @author ok1996
 */
@Configuration
@ComponentScan(value = {"cn.iosd.base.param"})
@MapperScan(basePackages = "cn.iosd.base.param.mapper")
@ConditionalOnProperty(value = "simple.base.param.enabled", havingValue = "true", matchIfMissing = true)
public class BaseParamAutoConfiguration {

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
