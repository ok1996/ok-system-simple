package cn.iosd.starter.s3.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * @author ok1996
 */
@Data
@Configuration
@ConfigurationProperties("simple.s3")
public class S3Properties {
    private String endpoint;

    private String accessKey;

    private String secretKey;

}
