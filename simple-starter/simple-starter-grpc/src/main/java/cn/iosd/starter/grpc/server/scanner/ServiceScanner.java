package cn.iosd.starter.grpc.server.scanner;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author ok1996
 */
@Configuration
@ComponentScan(value = {"cn.iosd.starter.grpc.server"})
@ConditionalOnProperty(prefix = "simple.grpc.server", name = "enabled", havingValue = "true")
public class ServiceScanner {
}
