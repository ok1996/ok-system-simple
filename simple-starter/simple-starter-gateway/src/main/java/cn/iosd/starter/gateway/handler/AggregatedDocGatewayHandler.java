package cn.iosd.starter.gateway.handler;

import com.github.xiaoymin.knife4j.spring.gateway.Knife4jGatewayProperties;
import com.github.xiaoymin.knife4j.spring.gateway.discover.ServiceDiscoverHandler;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 聚合网关文档所需处理器
 * <br/>
 * 支持内容：网关层配置过滤器为->将请求的路径前缀去除
 *
 * @author ok1996
 */
@Primary
@Component
public class AggregatedDocGatewayHandler extends ServiceDiscoverHandler {


    public AggregatedDocGatewayHandler(Knife4jGatewayProperties knife4jGatewayProperties) {
        super(knife4jGatewayProperties);
    }

    /**
     * 当网关层配置过滤器为：将请求的路径前缀去除，例如配置内容如下
     * <br/>
     * spring.cloud.gateway.discovery.locator.filters[0].name=StripPrefix
     * <br/>
     * spring.cloud.gateway.discovery.locator.filters[0].args.parts=0
     * <br/>
     * 则所需聚合文档应移除ContextPath避免出现重复路径
     *
     * @param service 服务发现列表
     */
    @Override
    public void discover(List<String> service) {
        super.discover(service);
        this.getGatewayResources().forEach(i -> i.setContextPath(""));
    }
}