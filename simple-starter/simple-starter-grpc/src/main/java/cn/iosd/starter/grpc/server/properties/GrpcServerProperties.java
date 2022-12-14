package cn.iosd.starter.grpc.server.properties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author ok1996
 */
@Data
@Configuration
@ConfigurationProperties("simple.grpc.server")
public class GrpcServerProperties {

    private Integer port = 7650;

}
