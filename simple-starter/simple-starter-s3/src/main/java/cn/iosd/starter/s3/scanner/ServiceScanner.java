package cn.iosd.starter.s3.scanner;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author ok1996
 */
@Configuration
@ComponentScan(value = {"cn.iosd.starter.s3"})
@ConditionalOnProperty(prefix = "simple.s3", name = "enabled", havingValue = "true" , matchIfMissing = true)
public class ServiceScanner {
}
