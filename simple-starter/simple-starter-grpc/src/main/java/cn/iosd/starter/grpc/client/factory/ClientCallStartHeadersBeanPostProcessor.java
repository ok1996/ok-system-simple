package cn.iosd.starter.grpc.client.factory;

import cn.iosd.starter.grpc.client.interceptor.ClientCallStartHeaders;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Bean 初始化时捕获 ClientCallStartHeaders 类型的 Bean 及其名称
 *
 * @author ok1996
 */
@Component
public class ClientCallStartHeadersBeanPostProcessor implements BeanPostProcessor {

    private final Map<String, ClientCallStartHeaders> beanNameMap = new ConcurrentHashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ClientCallStartHeaders) {
            beanNameMap.put(beanName, (ClientCallStartHeaders) bean);
        }
        return bean;
    }

    public Map<String, ClientCallStartHeaders> getClientCallStartHeadersMap() {
        return beanNameMap;
    }
}
