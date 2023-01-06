package cn.iosd.starter.grpc.client.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ok1996
 */
@Configuration
@ConfigurationProperties("simple.grpc.client")
public class GrpcClientProperties {

    private Map<String, GrpcChannelProperties> channel = new ConcurrentHashMap<>();

    public Map<String, GrpcChannelProperties> getChannel() {
        return channel;
    }

    public void setChannel(Map<String, GrpcChannelProperties> channel) {
        this.channel = channel;
    }
}
