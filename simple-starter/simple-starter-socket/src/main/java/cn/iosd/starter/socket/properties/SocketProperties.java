package cn.iosd.starter.socket.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author ok1996
 */
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

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getPingInterval() {
        return pingInterval;
    }

    public void setPingInterval(Integer pingInterval) {
        this.pingInterval = pingInterval;
    }

    public Integer getPingTimeout() {
        return pingTimeout;
    }

    public void setPingTimeout(Integer pingTimeout) {
        this.pingTimeout = pingTimeout;
    }

    public Integer getUpgradeTimeout() {
        return upgradeTimeout;
    }

    public void setUpgradeTimeout(Integer upgradeTimeout) {
        this.upgradeTimeout = upgradeTimeout;
    }
}
