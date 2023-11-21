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
    private Redisson redisson;

    public RedissonManager(RedissonProperties redissonProperties) {
        Config config = RedissonConfigFactory.getInstance().createConfig(redissonProperties);
        redisson = (Redisson) Redisson.create(config);
    }

    public Redisson getRedisson() {
        return redisson;
    }

}


