package cn.iosd.starter.grpc.client.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ok1996
 */
@Data
@Configuration
@ConfigurationProperties("simple.grpc.client")
public class GrpcClientProperties {

    private Map<String, GrpcChannelProperties> channel = new ConcurrentHashMap<>();

}
