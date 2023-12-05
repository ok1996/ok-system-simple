package cn.iosd.starter.grpc.client;

import cn.iosd.starter.grpc.client.annotation.GrpcClient;
import cn.iosd.starter.grpc.client.vo.GrpcClientBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * 存储使用到GrpcClient注解的Bean对象地址
 *
 * @author ok1996
 */
@Component
public class InitializeGrpcClientBeans implements BeanPostProcessor {
    private final List<GrpcClientBean> injections = new ArrayList<>();

    public List<GrpcClientBean> getInjections() {
        return injections;
    }

    @Override
    public Object postProcessAfterInitialization(@Nullable Object bean,@Nullable String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(@Nullable Object bean, @Nullable String beanName) throws BeansException {
        if (bean == null) {
            return null;
        }
        Stream.of(bean.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(GrpcClient.class))
                .forEach(field -> {
                    GrpcClient annotation = field.getAnnotation(GrpcClient.class);
                    GrpcClientBean registry = new GrpcClientBean(bean, annotation, field);
                    injections.add(registry);
                });
        return bean;
    }

}
