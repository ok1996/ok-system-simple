package cn.iosd.starter.grpc.client;

import cn.iosd.starter.grpc.client.annotation.GrpcClient;
import cn.iosd.starter.grpc.client.properties.GrpcChannelProperties;
import cn.iosd.starter.grpc.client.properties.GrpcClientProperties;
import cn.iosd.starter.grpc.client.vo.GrpcChannel;
import cn.iosd.starter.grpc.client.vo.GrpcClientBeans;
import io.grpc.ManagedChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 涉及的Bean对象注入GrpcChannel
 *
 * @author ok1996
 */
@Configuration
public class GrpcClientService implements InitializingBean {
    private static final Logger log = LoggerFactory.getLogger(GrpcClientService.class);

    @Autowired
    private GrpcClientProperties grpcClientProperties;

    @Autowired
    private InitializeGrpcClientBeans beanInjection;

    @Override
    public void afterPropertiesSet() {
        if (beanInjection == null || beanInjection.getGrpcClientBeans() == null) {
            return;
        }

        GrpcClientBeans grpcClientBeans = beanInjection.getGrpcClientBeans();
        List<GrpcClientBeans.GrpcClientBean> grpcClientBeanList = grpcClientBeans.getInjections();

        for (GrpcClientBeans.GrpcClientBean grpcClientBean : grpcClientBeanList) {
            GrpcClient annotation = grpcClientBean.client();
            Class<?> type = grpcClientBean.field().getType();
            Field field = grpcClientBean.field();
            Object bean = grpcClientBean.bean();

            if (grpcClientProperties.getChannel() == null || grpcClientProperties.getChannel().get(annotation.value()) == null) {
                log.error("配置文件缺失请核查，GrpcChannel未装配值：{}", annotation.value());
                continue;
            }

            GrpcChannelProperties properties = grpcClientProperties.getChannel().get(annotation.value());

            long timeout;
            if (-1 != annotation.timeout()) {
                timeout = annotation.timeout();
            } else {
                timeout = grpcClientProperties.getTimeout();
            }

            ManagedChannel client = GrpcChannel.getChannel(properties.getAddress(), timeout);
            Object object = GrpcChannel.getBlockingStub(client, type);

            boolean accessible = field.canAccess(bean);
            ReflectionUtils.makeAccessible(field);

            try {
                field.set(bean, object);
                log.info("完成 {} 的 GrpcChannel 装配;调用超时时间为 {} 毫秒", annotation.value(), timeout);
            } catch (IllegalAccessException e) {
                String message = String.format("对象 %s 注入配置 GrpcChannel 异常：", bean.getClass().getSimpleName());
                log.error(message, e);
                throw new IllegalStateException(message, e);
            } finally {
                field.setAccessible(accessible);
            }
        }
    }
}
