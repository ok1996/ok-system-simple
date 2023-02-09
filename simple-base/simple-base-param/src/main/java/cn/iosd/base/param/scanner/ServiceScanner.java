package cn.iosd.base.param.scanner;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author ok1996
 */
@Configuration
@ComponentScan(value = {"cn.iosd.base.param"})
@MapperScan(basePackages = "cn.iosd.base.param.mapper")
@ConditionalOnProperty(value = "simple.base.param.enabled", havingValue = "true", matchIfMissing = true)
public class ServiceScanner {
}
