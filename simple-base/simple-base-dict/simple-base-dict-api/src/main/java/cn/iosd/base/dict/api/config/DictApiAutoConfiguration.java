package cn.iosd.base.dict.api.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author ok1996
 */
@Configuration
@EnableFeignClients(basePackages = {"cn.iosd.base.dict.api.feign"})
@ComponentScan(value = {"cn.iosd.base.dict.api"})
public class DictApiAutoConfiguration {
}
