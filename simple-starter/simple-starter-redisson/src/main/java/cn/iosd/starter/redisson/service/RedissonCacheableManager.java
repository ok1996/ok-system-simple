package cn.iosd.starter.redisson.service;

import cn.iosd.starter.redisson.properties.RedissonCacheableProperties;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建CacheManager并配置Cacheable注解常用配置及自定义CacheName对应配置
 *
 * @author ok1996
 */
@EnableCaching
@ConditionalOnProperty(value = {"simple.redisson.cacheable.enabled", "simple.redisson.enabled"}, havingValue = "true")
public class RedissonCacheableManager {

    private static final Logger log = LoggerFactory.getLogger(RedissonCacheableManager.class);

    @Autowired
    private RedissonCacheableProperties properties;

    @Autowired(required = false)
    private RedissonManager redissonManager;

    @Bean
    public CacheManager cacheManager() {
        Map<String, CacheConfig> config = new HashMap<String, CacheConfig>(3);
        config.put("cacheExpiresAfterTenMinutes", new CacheConfig(10 * 60 * 1000, 10 * 60 * 1000));
        config.put("cacheExpiresAfterOneHour", new CacheConfig(60 * 60 * 1000, 60 * 60 * 1000));
        config.put("cacheNeverExpires", new CacheConfig());
        log.info("Cacheable默认CacheName配置类已装配：[cacheNeverExpires,cacheExpiresAfterTenMinutes,cacheExpiresAfterOneHour]");
        if (properties.getConfig() != null) {
            properties.getConfig().forEach((cacheName, cacheExpireVo) -> {
                Long ttl = cacheExpireVo.getTtl();
                Long maxIdleTime = cacheExpireVo.getMaxIdleTime();
                CacheConfig cacheConfig = new CacheConfig(ttl, maxIdleTime);
                config.put(cacheName, cacheConfig);
                log.info("Cacheable自定义CacheName配置类已装配，CacheName：{}, ttl:{}毫秒, maxIdleTime:{}毫秒", cacheName, ttl, maxIdleTime);
            });
        }
        return new RedissonSpringCacheManager(redissonManager.getRedisson(), config);
    }
}