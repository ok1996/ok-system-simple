package cn.iosd.starter.grpc.server;

import cn.iosd.starter.grpc.server.annotation.GrpcService;
import cn.iosd.starter.grpc.server.interceptor.CustomServerInterceptor;
import cn.iosd.starter.grpc.server.interceptor.ServiceCallStartHeaders;
import cn.iosd.starter.grpc.server.properties.GrpcServerProperties;
import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;


/**
 * 启动服务端
 * <p>
 * 服务端口读取配置项：simple.grpc.server.port
 * <p>
 * 扫描使用GrpcService注解的Bean->添加到io.grpc.ServerBuilder
 *
 * @author ok1996
 */
@Service
public class GrpcServerService implements InitializingBean, ApplicationContextAware {
    private static final Logger log = LoggerFactory.getLogger(GrpcServerService.class);

    @Autowired
    private GrpcServerProperties grpcServerProperties;

    @Autowired(required = false)
    private ServiceCallStartHeaders serviceCallStartHeaders;

    private Server server;

    private static ApplicationContext context = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws IOException {
        GrpcServerService server = new GrpcServerService();
        server.start(grpcServerProperties.getPort(), serviceCallStartHeaders);
        log.info("GrpcServer start port:{}", grpcServerProperties.getPort());
    }

    private void start(final int port, ServiceCallStartHeaders serviceCallStartHeaders) throws IOException {
        ServerBuilder<?> serverBuilder = ServerBuilder.forPort(port);
        if (context != null) {
            Map<String, Object> beansWithAnnotationMap = context.getBeansWithAnnotation(GrpcService.class);
            beansWithAnnotationMap.forEach((key, value) -> {
                if (value instanceof BindableService) {
                    serverBuilder.addService((BindableService) value);
                    log.info("GrpcServer add service: {}", value.getClass().getName());
                } else {
                    log.warn("GrpcServer ignore non-BindableService class: {}", value.getClass().getName());
                }
            });
        }
        CustomServerInterceptor customServerInterceptor = new CustomServerInterceptor(serviceCallStartHeaders);
        serverBuilder.intercept(customServerInterceptor);
        this.server = serverBuilder.build();
        this.server.start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            this.server.shutdown();
            log.info("GrpcServer shut down");
        }));
    }


}
