package cn.iosd.base.s3.service.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author ok1996
 */
@Configuration
@ComponentScan(value = {"cn.iosd.base.s3.service"})
@ConditionalOnProperty(value = "simple.scan.service.enabled", havingValue = "true", matchIfMissing = true)
public class S3ScanConfiguration {
}
