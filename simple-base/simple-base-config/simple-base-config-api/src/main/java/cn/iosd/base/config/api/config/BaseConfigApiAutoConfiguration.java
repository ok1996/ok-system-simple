package cn.iosd.base.config.api.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author ok1996
 */
@Configuration
@EnableFeignClients(basePackages = {"cn.iosd.base.config.api.feign"})
@ComponentScan(value = {"cn.iosd.base.config.api"})
public class BaseConfigApiAutoConfiguration {
}
