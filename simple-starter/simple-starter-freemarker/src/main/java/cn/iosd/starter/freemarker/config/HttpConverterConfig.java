package cn.iosd.starter.freemarker.config;

import cn.iosd.starter.freemarker.properties.FreemarkerProperties;
import cn.iosd.starter.freemarker.vo.ResourceVo;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 配置项注入映射资源
 *
 * @author ok1996
 */
@Configuration
public class HttpConverterConfig implements WebMvcConfigurer {

    @Resource
    private FreemarkerProperties freemarkerProperties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        List<ResourceVo> resourceVoList = freemarkerProperties.getResourceVoList();
        resourceVoList.stream().forEach(v -> {
            registry.addResourceHandler(v.getResourceHandler())
                    .addResourceLocations(v.getResourceLocations());
        });
    }

}
