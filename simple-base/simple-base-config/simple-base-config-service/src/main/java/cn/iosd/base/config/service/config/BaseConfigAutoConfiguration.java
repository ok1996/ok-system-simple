package cn.iosd.base.config.service.config;

import cn.iosd.starter.datasource.mybatis.MapperLocations;
import org.flywaydb.core.Flyway;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 配置MapperScan、MapperLocations、Flyway
 *
 * @author ok1996
 */
@Configuration
@MapperScan(basePackages = "cn.iosd.base.config.service.mapper")
public class BaseConfigAutoConfiguration {

    @Bean
    public Flyway baseConfigFlyway(DataSource dataSource) {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/mysql/base/config")
                .baselineOnMigrate(true)
                .table("base_config_flyway_schema_history")
                .load();
        flyway.migrate();
        return flyway;
    }

    @Bean
    public MapperLocations baseConfigMapperLocations() {
        return MapperLocations.of("classpath*:/cn/iosd/base/config/service/mapper/**/*Mapper.xml");
    }
}
