package cn.iosd.demo.datasource.config;

import org.flywaydb.core.Flyway;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 配置MapperScan、Flyway
 *
 * @author ok1996
 */
@Configuration
@MapperScan("cn.iosd.demo.datasource.mapper")
public class DemoDatasourceAutoConfiguration {

    /**
     * 配置Flyway迁移脚本信息
     *
     * @param dataSource 数据源
     * @return Flyway对象
     */
    @Bean
    public Flyway datasourceFlyway(DataSource dataSource) {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/mysql/ds")
                .baselineOnMigrate(true)
                .table("datasource_flyway_schema_history")
                .load();
        flyway.migrate();
        return flyway;
    }
}
