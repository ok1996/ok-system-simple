package ${packageConfig};

import org.flywaydb.core.Flyway;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.sql.DataSource;

/**
 * <p>
 * 配置MapperScan、Flyway、ComponentScan以及是否启用
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

}
