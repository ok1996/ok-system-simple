package ${packageConfig};

import cn.iosd.starter.datasource.mybatis.provider.MapperLocationsProvider;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 接口配置 mapper-locations
 *
 * @author ${author}
 */
@Component
public class ${ModuleName}MapperLocationsProvider implements MapperLocationsProvider {
    @Override
    public List<String> getMapperLocations() {
        return List.of("classpath*:${mapperLocations}**/*Mapper.xml");
    }
}
