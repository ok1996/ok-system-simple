package ${packageConfig};

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
 * <p>
 * 配置MapperScan、MapperLocations、Flyway、ComponentScan以及是否启用
 * </p>
 *
 * @author ${author}
 */
@Configuration
@MapperScan("${package.Mapper}")
@ComponentScan(value = {"${packageParent}"})
@ConditionalOnProperty(value = "simple.scan.enabled", havingValue = "true", matchIfMissing = true)
public class ${ModuleName}AutoConfig {

    @Bean
    public Flyway ${ModuleName?lower_case}Flyway(DataSource dataSource) {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/mysql/${ModuleName?lower_case}")
                .baselineOnMigrate(true)
                .table("${ModuleName?lower_case}_flyway_schema_history")
                .load();
        flyway.migrate();
        return flyway;
    }

    @Bean
    public MapperLocations ${ModuleName?lower_case}Locations() {
        return new MapperLocations(List.of("classpath*:${mapperLocations}**/*Mapper.xml"));
    }
}
