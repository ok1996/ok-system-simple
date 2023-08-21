package cn.iosd.base.param.service.config;

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
@MapperScan(basePackages = "cn.iosd.base.param.service.mapper")
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

    @Bean
    public MapperLocations paramMapperLocations() {
        return new MapperLocations("classpath*:/cn/iosd/base/param/service/mapper/**/*Mapper.xml");
    }
}
