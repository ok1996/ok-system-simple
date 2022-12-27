package cn.iosd.starter.grpc.client;

import cn.iosd.starter.grpc.client.annotation.GrpcClient;
import cn.iosd.starter.grpc.client.properties.GrpcChannelProperties;
import cn.iosd.starter.grpc.client.properties.GrpcClientProperties;
import cn.iosd.starter.grpc.client.vo.GrpcChannel;
import cn.iosd.starter.grpc.client.vo.GrpcClientBeans;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Configuration
public class GrpcClientService implements InitializingBean {

    @Autowired
    private GrpcClientProperties grpcClientProperties;

    @Autowired
    private InitializeGrpcClientBeans beanInjection;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (beanInjection != null && beanInjection.getGrpcClientBeans() != null
                && beanInjection.getGrpcClientBeans().getInjections() != null) {

            GrpcClientBeans grpcClientBeans = beanInjection.getGrpcClientBeans();
            List<GrpcClientBeans.GrpcClientBean> lsGrpcClientBean = grpcClientBeans.getInjections();
            lsGrpcClientBean.forEach(v -> {
                GrpcClient annotation = v.getClient();
                Class<?> type = v.getField().getType();
                Field field = v.getField();
                Object bean = v.getBean();

                if (grpcClientProperties.getChannel()!=null
                        &&grpcClientProperties.getChannel().get(annotation.value())!=null){

                    GrpcChannelProperties properties = grpcClientProperties.getChannel().get(annotation.value());
                    GrpcChannel client = new GrpcChannel(properties.getAddress());
                    Object object = client.getBlockingStub(type);
                    boolean accessible = field.isAccessible();
                    ReflectionUtils.makeAccessible(field);
                    try {
                        field.set(bean, object);
                    } catch (IllegalAccessException e) {
                        log.error("对象Bean注入配置GrpcChannel异常：", e.getMessage());
                        throw new RuntimeException(e);
                    }
                    field.setAccessible(accessible);
                }else {
                    log.error("配置文件缺失请核查，GrpcChannel未装配值：{}",annotation.value());
                }
            });
            log.info("完成GrpcChannel装配");
        }
    }
}
