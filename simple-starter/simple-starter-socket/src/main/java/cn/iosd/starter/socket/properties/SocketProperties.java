package cn.iosd.starter.socket.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author ok1996
 */
@Data
@Configuration
@ConfigurationProperties("simple.socket")
public class SocketProperties {

    private String hostName = "0.0.0.0";

    /**
     * socket端口
     */
    private Integer port;
    /**
     * Ping消息间隔（毫秒）
     */
    private Integer pingInterval;
    /**
     * Ping消息超时时间（毫秒）
     */
    private Integer pingTimeout;
    /**
     * HTTP握手升级为ws协议超时时间(毫秒)
     */
    private Integer upgradeTimeout;

}
