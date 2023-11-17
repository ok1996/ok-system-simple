package cn.iosd.starter.grpc.server;

import cn.iosd.starter.grpc.server.interceptor.CustomServerInterceptor;
import cn.iosd.starter.grpc.server.interceptor.ServiceCallStartHeaders;
import cn.iosd.starter.grpc.server.properties.GrpcServerProperties;
import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


/**
 * 启动Grpc服务端
 * <p>
 * 服务端口读取配置项：simple.grpc.server.port
 * <p>
 *
 * @author ok1996
 */
@Service
public class GrpcServerService {
    private static final Logger log = LoggerFactory.getLogger(GrpcServerService.class);

    @Autowired
    private GrpcServerProperties grpcServerProperties;

    @Autowired(required = false)
    private ServiceCallStartHeaders serviceCallStartHeaders;

    @Autowired(required = false)
    private List<BindableService> grpcServices;

    private Server server;

    @PostConstruct
    public void startGrpcServer() throws IOException {
        ServerBuilder<?> serverBuilder = ServerBuilder.forPort(grpcServerProperties.getPort());
        if (grpcServices != null && !grpcServices.isEmpty()) {
            grpcServices.forEach(service -> {
                serverBuilder.addService(service);
                log.info("GrpcServer add service: {}", service.getClass().getName());
            });
        } else {
            log.warn("No GrpcService found to add to the server");
        }
        CustomServerInterceptor customServerInterceptor = new CustomServerInterceptor(serviceCallStartHeaders);
        serverBuilder.intercept(customServerInterceptor);
        this.server = serverBuilder.build();
        this.server.start();
        log.info("GrpcServer start port: {}", grpcServerProperties.getPort());

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            this.server.shutdown();
            log.info("GrpcServer shut down");
        }));
    }
}
