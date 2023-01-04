package cn.iosd.starter.grpc.server.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author ok1996
 */
@Configuration
@ConfigurationProperties("simple.grpc.server")
public class GrpcServerProperties {
    private Integer port = 7650;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
