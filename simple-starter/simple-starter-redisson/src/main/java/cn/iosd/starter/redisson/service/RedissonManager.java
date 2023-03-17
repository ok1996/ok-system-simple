package cn.iosd.starter.redisson.service;

import cn.iosd.starter.redisson.properties.RedissonProperties;
import org.redisson.Redisson;
import org.redisson.config.Config;


/**
 * 初始化redisson实例
 *
 * @author ok1996
 */
public class RedissonManager {
    private Config config;

    private Redisson redisson;

    public RedissonManager(RedissonProperties redissonProperties) {
        try {
            config = RedissonConfigFactory.getInstance().createConfig(redissonProperties);
            redisson = (Redisson) Redisson.create(config);
        } catch (Exception e) {
            throw new IllegalArgumentException("Redisson init error. Please check the configuration");
        }
    }

    public Redisson getRedisson() {
        return redisson;
    }

}


