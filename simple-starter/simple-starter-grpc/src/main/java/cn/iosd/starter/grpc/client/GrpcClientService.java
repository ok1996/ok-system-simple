package cn.iosd.starter.grpc.client;

import cn.iosd.starter.grpc.client.annotation.GrpcClient;
import cn.iosd.starter.grpc.client.interceptor.ClientCallStartHeaders;
import cn.iosd.starter.grpc.client.properties.GrpcChannelProperties;
import cn.iosd.starter.grpc.client.properties.GrpcClientProperties;
import cn.iosd.starter.grpc.client.vo.GrpcChannel;
import cn.iosd.starter.grpc.client.vo.GrpcClientBeans;
import io.grpc.ManagedChannel;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired(required = false)
    private List<ClientCallStartHeaders> clientCallStartHeadersList;

    private Map<String, ClientCallStartHeaders> clientCallStartHeadersMap = new HashMap<>();

    /**
     * 初始化时将ClientCallStartHeaders对象与beanName进行关联
     */
    @PostConstruct
    public void init() {
        for (ClientCallStartHeaders service : clientCallStartHeadersList) {
            String beanName = applicationContext.getBeanNamesForType(service.getClass())[0];
            clientCallStartHeadersMap.put(beanName, service);
        }
    }

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
            ClientCallStartHeaders headers = null;
            if (!CollectionUtils.isEmpty(clientCallStartHeadersList)) {
                if (clientCallStartHeadersList.size() == 1 && StringUtils.isBlank(annotation.headerBeanName())) {
                    headers = clientCallStartHeadersList.get(0);
                } else {
                    String beanName = annotation.headerBeanName();
                    headers = StringUtils.isNotBlank(beanName) ? clientCallStartHeadersMap.get(beanName) : null;
                }
            }
            ManagedChannel client = GrpcChannel.getChannel(properties.getAddress(), timeout, headers);
            Object object = GrpcChannel.getBlockingStub(client, type);

            boolean accessible = field.canAccess(bean);
            ReflectionUtils.makeAccessible(field);

            try {
                field.set(bean, object);
                log.info("完成 {} 的 GrpcChannel 装配;调用超时时间为 {} 毫秒;headers为 {}", annotation.value(), timeout, headers);
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
