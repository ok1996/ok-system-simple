package cn.iosd.starter.socket.config;

import cn.iosd.starter.redisson.service.RedissonManager;
import cn.iosd.starter.socket.handler.NettyExceptionListener;
import cn.iosd.starter.socket.properties.SocketProperties;
import com.corundumstudio.socketio.AckMode;
import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
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
@ConditionalOnProperty(prefix = "simple.socket", name = "enabled", havingValue = "true")
public class NettySocketConfig {
    private static final Logger log = LoggerFactory.getLogger(NettySocketConfig.class);

    @Autowired
    private SocketProperties socketProperties;

    @Autowired
    private NettyExceptionListener nettyExceptionListener;

    @Autowired(required = false)
    private RedissonManager redissonManager;

    @Autowired(required = false)
    private AuthorizationListener authorizationListener;

    @Bean
    public SocketIOServer socketIoServer() {
        /*
         * 创建Socket，并设置监听端口
         */
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname(socketProperties.getHostName());
        // 设置监听端口
        config.setPort(socketProperties.getPort());
        // 协议升级超时时间（毫秒），默认10000。HTTP握手升级为ws协议超时时间
        config.setUpgradeTimeout(socketProperties.getUpgradeTimeout());
        // Ping消息间隔（毫秒），默认25000。客户端向服务器发送一条心跳消息间隔
        config.setPingInterval(socketProperties.getPingInterval());
        // Ping消息超时时间（毫秒），默认60000，这个时间间隔内没有接收到心跳消息就会发送超时事件
        config.setPingTimeout(socketProperties.getPingTimeout());
        // 基于redisson
        if (redissonManager != null) {
            config.setStoreFactory(createRedissonStoreFactory());
            log.info("SocketIOServer开启Redis集群模式");
        }
        //异常处理
        config.setExceptionListener(nettyExceptionListener);
        //手动确认
        config.setAckMode(AckMode.MANUAL);

        if (authorizationListener != null) {
            config.setAuthorizationListener(authorizationListener);
        }

        config.setBossThreads(1);

        SocketConfig sockConfig = new SocketConfig();
        // 解决SOCKET服务端重启"Address already in use"异常
        sockConfig.setReuseAddress(true);
        sockConfig.setTcpKeepAlive(false);
        config.setSocketConfig(sockConfig);

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

    private RedissonStoreFactory createRedissonStoreFactory() {
        RedissonStoreFactory redissonStoreFactory = new RedissonStoreFactory(redissonManager.getRedisson());
        return redissonStoreFactory;
    }
}