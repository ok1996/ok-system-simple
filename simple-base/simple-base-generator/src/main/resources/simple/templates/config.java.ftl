package ${packageConfig};

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * 配置文件
 * </p>
 *
 * @author ${author}
 */
@Configuration
@MapperScan("${package.Mapper}")
public class ${ModuleName}AutoConfig {
}
