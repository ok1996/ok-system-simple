package cn.iosd.starter.redisson.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author ok1996
 */
@Data
@Configuration
@ConfigurationProperties("simple.redisson")
public class RedissonProperties {
    private String address;

    private String type;

    private String password;

    private Integer database;
}
