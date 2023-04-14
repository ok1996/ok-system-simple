package cn.iosd.starter.redisson.config;

import cn.iosd.starter.redisson.properties.RedissonProperties;
import cn.iosd.starter.redisson.service.RedissonCacheService;
import cn.iosd.starter.redisson.service.RedissonManager;
import cn.iosd.starter.redisson.service.RedissonLockService;
import org.redisson.Redisson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * Redisson自动化配置
 *
 * @author ok1996
 */
@Configuration
@ConditionalOnClass(Redisson.class)
@ConditionalOnProperty(prefix = "simple.redisson", name = "enabled", havingValue = "true")
public class RedissonAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(RedissonAutoConfiguration.class);

    @Bean
    @ConditionalOnMissingBean
    @Order(value = 2)
    public RedissonLockService redissonLock(RedissonManager redissonManager) {
        RedissonLockService redissonLock = new RedissonLockService(redissonManager);
        return redissonLock;
    }

    @Bean
    @ConditionalOnMissingBean
    @Order(value = 2)
    public RedissonCacheService redissonCache(RedissonManager redissonManager) {
        RedissonCacheService redissonLock = new RedissonCacheService(redissonManager);
        return redissonLock;
    }

    @Bean
    @ConditionalOnMissingBean
    @Order(value = 1)
    public RedissonManager redissonManager(RedissonProperties redissonProperties) {
        RedissonManager redissonManager =
                new RedissonManager(redissonProperties);
        log.info("[RedissonManager]组装完毕,当前连接方式:" + redissonProperties.getType() +
                ",连接地址:" + redissonProperties.getAddress());
        return redissonManager;
    }
}

