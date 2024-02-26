package cn.iosd.starter.socketio.config;

import cn.iosd.starter.redisson.manager.RedissonManager;
import cn.iosd.starter.socketio.properties.SocketIOProperties;
import com.corundumstudio.socketio.AckMode;
import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import com.corundumstudio.socketio.listener.ExceptionListener;
import com.corundumstudio.socketio.store.RedissonStoreFactory;
import com.corundumstudio.socketio.store.pubsub.PubSubStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author ok1996
 */
@Configuration
@ConditionalOnProperty(prefix = "simple.socketio", name = "enabled", havingValue = "true")
public class SocketIOConfig {
    private static final Logger log = LoggerFactory.getLogger(SocketIOConfig.class);

    @Autowired
    private SocketIOProperties socketIOProperties;

    @Autowired(required = false)
    private ExceptionListener exceptionListener;

    @Autowired(required = false)
    private RedissonManager redissonManager;

    @Autowired(required = false)
    private AuthorizationListener authorizationListener;

    @Bean
    public SocketIOServer socketIoServer() {
        com.corundumstudio.socketio.Configuration config = createSocketConfiguration();
        return new SocketIOServer(config);
    }

    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
        return new SpringAnnotationScanner(socketServer);
    }

    @Bean
    public PubSubStore pubSubStore() {
        return socketIoServer().getConfiguration().getStoreFactory().pubSubStore();
    }

    private com.corundumstudio.socketio.Configuration createSocketConfiguration() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname(socketIOProperties.getHostName());
        config.setPort(socketIOProperties.getPort());
        config.setUpgradeTimeout(socketIOProperties.getUpgradeTimeout());
        config.setPingInterval(socketIOProperties.getPingInterval());
        config.setPingTimeout(socketIOProperties.getPingTimeout());
        configureRedisson(config);
        configureExceptionHandling(config);
        config.setAckMode(AckMode.MANUAL);
        configureAuthorization(config);
        config.setBossThreads(1);
        configureSocket(config);
        return config;
    }

    private void configureRedisson(com.corundumstudio.socketio.Configuration config) {
        if (redissonManager != null) {
            config.setStoreFactory(new RedissonStoreFactory(redissonManager.getRedisson()));
            log.info("SocketIOServer开启集群模式-使用RedissonStoreFactory");
        } else {
            log.info("SocketIOServer使用单机模式");
        }
    }

    private void configureExceptionHandling(com.corundumstudio.socketio.Configuration config) {
        if (exceptionListener != null) {
            config.setExceptionListener(exceptionListener);
            log.info("SocketIOServer开启异常侦听器");
        }
    }

    private void configureAuthorization(com.corundumstudio.socketio.Configuration config) {
        if (authorizationListener != null) {
            config.setAuthorizationListener(authorizationListener);
            log.info("SocketIOServer开启鉴权侦听器:{}", authorizationListener.getClass().getName());
        }
    }

    private void configureSocket(com.corundumstudio.socketio.Configuration config) {
        SocketConfig sockConfig = new SocketConfig();
        sockConfig.setReuseAddress(true);
        sockConfig.setTcpKeepAlive(false);
        config.setSocketConfig(sockConfig);
    }
}
