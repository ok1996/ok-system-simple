package cn.iosd.base.param.api.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author ok1996
 */
@Configuration
@EnableFeignClients(basePackages = {"cn.iosd.base.param.api.feign"})
@ComponentScan(value = {"cn.iosd.base.param.api"})
public class BaseParamApiAutoConfiguration {
}
