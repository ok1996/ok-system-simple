package cn.iosd.base.s3.api.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author ok1996
 */
@Configuration
@EnableFeignClients(basePackages = {"cn.iosd.base.s3.api.feign"})
@ComponentScan(value = {"cn.iosd.base.s3.api"})
public class S3ApiAutoConfiguration {
}
