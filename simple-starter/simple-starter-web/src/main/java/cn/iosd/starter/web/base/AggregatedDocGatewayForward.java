package cn.iosd.starter.web.base;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 聚合网关文档所需转发视图
 * <br/>
 * 支持内容：网关层配置过滤器为->将请求的路径前缀去除
 *
 * @author ok1996
 */
@Controller
public class AggregatedDocGatewayForward {

    /**
     * 当网关层配置过滤器为：将请求的路径前缀去除，例如配置内容如下
     * <br/>
     * spring.cloud.gateway.discovery.locator.filters[0].name=StripPrefix
     * <br/>
     * spring.cloud.gateway.discovery.locator.filters[0].args.parts=0
     * <br/>
     * 则所需聚合文档增加转发包含应用名称的接口地址
     */
    @GetMapping("/${spring.application.name}/v3/api-docs/default")
    public String forwardV3ApiDocsDefault() {
        return "forward:/v3/api-docs/default";
    }

    @GetMapping("/${spring.application.name}/v3/api-docs")
    public String forwardV3ApiDocs() {
        return "forward:/v3/api-docs";
    }
}
