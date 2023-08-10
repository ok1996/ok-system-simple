package cn.iosd.starter.web.base;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import java.util.HashMap;
import java.util.Map;

/**
 * 聚合网关文档所需转发视图
 * <br/>
 * 支持内容：网关层配置过滤器为->将请求的路径前缀去除
 *
 * @author ok1996
 */
@Configuration
public class AggregatedDocGatewayForward {

    @Value("${spring.application.name}")
    private String applicationName;

    /**
     * 当网关层配置过滤器为：将请求的路径前缀去除，例如配置内容如下
     * <br/>
     * spring.cloud.gateway.discovery.locator.filters[0].name=StripPrefix
     * <br/>
     * spring.cloud.gateway.discovery.locator.filters[0].args.parts=0
     * <br/>
     * 则所需聚合文档增加转发包含应用名称的接口地址
     */
    @Bean
    public SimpleUrlHandlerMapping customUrlHandlerMapping() {
        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setOrder(1);
        Map<String, Object> urlMap = new HashMap<>(4);
        urlMap.put("/" + applicationName + "/v3/api-docs/{param}", parameterizableViewController("v3/api-docs/{param}"));
        urlMap.put("/" + applicationName + "/v3/api-docs", parameterizableViewController("v3/api-docs"));
        urlMap.put("/" + applicationName + "/v3/api-docs/default", parameterizableViewController("v3/api-docs"));
        mapping.setUrlMap(urlMap);
        return mapping;
    }

    private ParameterizableViewController parameterizableViewController(String viewName) {
        ParameterizableViewController controller = new ParameterizableViewController();
        controller.setViewName("forward:/" + viewName);
        return controller;
    }
}

