package cn.iosd.starter.datasource.config;

import cn.iosd.starter.datasource.mybatis.provider.MapperLocationsProvider;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusPropertiesCustomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动配置 MybatisPlus 的 mapper-locations 属性，通过实现 MapperLocationsProvider 接口获取配置值
 * 注：会覆盖掉配置文件中的 mapperLocations 属性值
 *
 * @author ok1996
 */
@Configuration
@AutoConfigureAfter(MybatisPlusAutoConfiguration.class)
@ConditionalOnProperty(name = "simple.datasource.locations.enabled", havingValue = "true")
public class MapperLocationsAutoConfiguration {
    @Autowired(required = false)
    private List<MapperLocationsProvider> providers;

    /**
     * 配置 MybatisPlusPropertiesCustomizer 的 Bean
     * 自定义设置 MybatisPlusPropertiesCustomizer 的 setMapperLocations 方法，
     * 将 MapperLocationsProvider 实现类的 mapper-locations 值传入 MybatisPlusProperties 中
     *
     * @return 返回 MybatisPlusPropertiesCustomizer 的 Bean
     */
    @Bean
    public MybatisPlusPropertiesCustomizer mybatisPlusPropertiesCustomizer() {
        return (properties) -> {
            List<String> mapperLocations = new ArrayList<>();
            if (providers != null) {
                for (MapperLocationsProvider provider : providers) {
                    mapperLocations.addAll(provider.getMapperLocations());
                }
                properties.setMapperLocations(mapperLocations.toArray(new String[0]));
            }
        };
    }

}
