package cn.iosd.starter.grpc.client;

import cn.iosd.starter.grpc.client.annotation.GrpcClient;
import cn.iosd.starter.grpc.client.vo.GrpcClientBeans;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

/**
 * 存储使用到GrpcClient注解的Bean对象地址
 *
 * @author ok1996
 */
@Component
public class InitializeGrpcClientBeans implements BeanPostProcessor {
    final GrpcClientBeans grpcClientBeans = new GrpcClientBeans();

    public GrpcClientBeans getGrpcClientBeans() {
        return grpcClientBeans;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Stream.of(bean.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(GrpcClient.class))
                .forEach(field -> {
                    GrpcClient annotation = field.getAnnotation(GrpcClient.class);
                    GrpcClientBeans.GrpcClientBean registry = new GrpcClientBeans.GrpcClientBean(bean, annotation, field);
                    grpcClientBeans.add(registry);
                });
        return bean;
    }

}
