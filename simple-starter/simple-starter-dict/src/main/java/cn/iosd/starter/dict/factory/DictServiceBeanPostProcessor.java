package cn.iosd.starter.dict.factory;

import cn.iosd.starter.dict.service.DictService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Bean 初始化时捕获 DictService 类型的 Bean 及其名称
 *
 * @author ok1996
 */
@Component
public class DictServiceBeanPostProcessor implements BeanPostProcessor {

    private final Map<String, DictService> dictServiceMap = new ConcurrentHashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof DictService) {
            dictServiceMap.put(beanName, (DictService) bean);
        }
        return bean;
    }

    public Map<String, DictService> getDictServiceMap() {
        return dictServiceMap;
    }
}
