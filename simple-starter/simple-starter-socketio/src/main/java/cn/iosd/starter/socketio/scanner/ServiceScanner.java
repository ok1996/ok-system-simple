package cn.iosd.starter.socketio.scanner;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author ok1996
 */
@Configuration
@ComponentScan(value = {"cn.iosd.starter.socketio"})
@ConditionalOnProperty(prefix = "simple.socketio", name = "enabled", havingValue = "true")
public class ServiceScanner {
}
