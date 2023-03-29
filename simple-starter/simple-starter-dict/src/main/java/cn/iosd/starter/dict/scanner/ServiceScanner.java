package cn.iosd.starter.dict.scanner;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author ok1996
 */
@Configuration
@ComponentScan(value = {"cn.iosd.starter.dict"})
@ConditionalOnProperty(name = "simple.dict.enabled", havingValue = "true", matchIfMissing = true)
public class ServiceScanner {
}
