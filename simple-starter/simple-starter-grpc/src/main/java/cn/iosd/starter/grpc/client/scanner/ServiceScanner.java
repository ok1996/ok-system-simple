package cn.iosd.starter.grpc.client.scanner;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author ok1996
 */
@Configuration
@ComponentScan(value = {"cn.iosd.starter.grpc.client"})
@ConditionalOnProperty(prefix = "simple.grpc.client", name = "enabled", havingValue = "true")
public class ServiceScanner {
}
