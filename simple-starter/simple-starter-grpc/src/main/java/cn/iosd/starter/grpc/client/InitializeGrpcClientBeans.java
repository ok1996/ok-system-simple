package cn.iosd.starter.grpc.client;

import cn.iosd.starter.grpc.client.annotation.GrpcClient;
import cn.iosd.starter.grpc.client.vo.GrpcClientBeans;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

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
        ReflectionUtils.doWithFields(bean.getClass(), field -> {
            // 被指定注解  注解的field
            GrpcClient annotation = field.getAnnotation(GrpcClient.class);
            if (annotation == null) {
                return;
            }
            GrpcClientBeans.GrpcClientBean registry =
                    new GrpcClientBeans.GrpcClientBean(
                            bean,
                            annotation,
                            field);

            grpcClientBeans.add(registry);
        });
        return bean;
    }

}
