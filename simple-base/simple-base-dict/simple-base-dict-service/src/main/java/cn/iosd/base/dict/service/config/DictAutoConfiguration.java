package cn.iosd.base.dict.service.config;

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
@MapperScan(basePackages = "cn.iosd.base.dict.service.mapper")
public class DictAutoConfiguration {

    @Bean
    public Flyway dictFlyway(DataSource dataSource) {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/mysql/base/dict")
                .baselineOnMigrate(true)
                .table("base_dict_flyway_schema_history")
                .load();
        flyway.migrate();
        return flyway;
    }

    @Bean
    public MapperLocations dictMapperLocations() {
        return new MapperLocations("classpath*:/cn/iosd/base/dict/service/mapper/**/*Mapper.xml");
    }
}
