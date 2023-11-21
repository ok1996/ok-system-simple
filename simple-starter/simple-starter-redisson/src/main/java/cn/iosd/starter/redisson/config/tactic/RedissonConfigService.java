package cn.iosd.starter.redisson.config.tactic;


import cn.iosd.starter.redisson.properties.RedissonProperties;
import org.redisson.config.Config;


/**
 * @author ok1996
 */
public interface RedissonConfigService {

    /**
     * 根据不同的Redis配置策略创建对应的Config
     *
     * @param redissonProperties
     * @return
     */
    Config createRedissonConfig(RedissonProperties redissonProperties);
}
