package cn.iosd.starter.cloud.config;

import feign.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Feign配置日志级别项
 *
 * @author ok1996
 */
@Configuration
@ConditionalOnProperty(name = "simple.feign.logger.enabled", havingValue = "true", matchIfMissing = true)
public class FeignLoggerConfig {

    @Bean
    public Logger.Level feignLoggerLeave() {
        return Logger.Level.FULL;
    }
}
