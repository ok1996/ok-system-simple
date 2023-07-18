package cn.iosd.base.param.config;

import cn.iosd.starter.datasource.mybatis.MapperLocations;
import org.flywaydb.core.Flyway;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.List;

/**
 * 配置MapperScan、MapperLocations、Flyway、ComponentScan以及是否启用
 *
 * @author ok1996
 */
@Configuration
@MapperScan(basePackages = "cn.iosd.base.param.mapper")
@ComponentScan(value = {"cn.iosd.base.param"})
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

    @Bean
    public MapperLocations paramMapperLocations() {
        return new MapperLocations(List.of("classpath*:/cn/iosd/base/param/mapper/**/*Mapper.xml"));
    }
}
