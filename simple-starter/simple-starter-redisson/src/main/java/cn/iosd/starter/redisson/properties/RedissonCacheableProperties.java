package cn.iosd.starter.redisson.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * 配置Cacheable注解cacheName及对应过期时间
 *
 * @author ok1996
 */
@Configuration
@ConfigurationProperties("simple.redisson.cacheable")
public class RedissonCacheableProperties {
    /**
     * key:cacheName
     */
    private Map<String, CacheExpireVo> config;

    public Map<String, CacheExpireVo> getConfig() {
        return config;
    }

    public void setConfig(Map<String, CacheExpireVo> config) {
        this.config = config;
    }
}
