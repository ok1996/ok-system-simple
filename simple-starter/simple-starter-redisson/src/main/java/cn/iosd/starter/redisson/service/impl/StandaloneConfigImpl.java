package cn.iosd.starter.redisson.service.impl;

import cn.iosd.starter.redisson.properties.RedissonProperties;
import cn.iosd.starter.redisson.service.RedissonConfigService;
import org.apache.commons.lang3.StringUtils;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 单机部署
 *
 * @author ok1996
 */
public class StandaloneConfigImpl implements RedissonConfigService {
    private static final Logger log = LoggerFactory.getLogger(StandaloneConfigImpl.class);

    @Override
    public Config createRedissonConfig(RedissonProperties redissonProperties) {
        Config config = new Config();
        String address = redissonProperties.getAddress();
        String password = redissonProperties.getPassword();
        Integer database = redissonProperties.getDatabase();
        String redisAddr = RedissonProperties.REDIS_CONNECTION_PREFIX + address;
        config.useSingleServer().setAddress(redisAddr);
        config.useSingleServer().setDatabase(database);
        if (StringUtils.isNotBlank(password)) {
            config.useSingleServer().setPassword(password);
        }
        log.info("初始化[单体部署]方式 连接地址:" + address);
        return config;
    }
}
