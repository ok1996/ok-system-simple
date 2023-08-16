package cn.iosd.base.param.api.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author ok1996
 */
@Configuration
@EnableFeignClients(basePackages = {"cn.iosd.base.param.api.feign"})
@ComponentScan(value = {"cn.iosd.base.param.api"})
@ConditionalOnProperty(value = "simple.scan.api.enabled", havingValue = "true", matchIfMissing = true)
public class BaseParamApiAutoConfiguration {
}
