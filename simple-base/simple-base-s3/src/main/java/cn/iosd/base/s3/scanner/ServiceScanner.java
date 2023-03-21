package cn.iosd.base.s3.scanner;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author ok1996
 */
@Configuration
@ComponentScan(value = {"cn.iosd.base.s3"})
@ConditionalOnProperty(value = "simple.base.s3.enabled", havingValue = "true", matchIfMissing = true)
public class ServiceScanner {
}
