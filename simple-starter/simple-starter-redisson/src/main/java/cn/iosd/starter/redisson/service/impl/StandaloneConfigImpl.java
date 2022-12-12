package cn.iosd.starter.redisson.service.impl;


import cn.hutool.core.util.StrUtil;
import cn.iosd.starter.redisson.constant.RedisConnectionUrl;
import cn.iosd.starter.redisson.properties.RedissonProperties;
import cn.iosd.starter.redisson.service.RedissonConfigService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.config.Config;

/**
 * 单机部署
 *
 * @author ok1996
 */
@Slf4j
public class StandaloneConfigImpl implements RedissonConfigService {

    @Override
    public Config createRedissonConfig(RedissonProperties redissonProperties) {
        Config config = new Config();
        try {
            String address = redissonProperties.getAddress();
            String password = redissonProperties.getPassword();
            Integer database = redissonProperties.getDatabase();
            String redisAddr = RedisConnectionUrl.REDIS_CONNECTION_PREFIX.getValue() + address;
            config.useSingleServer().setAddress(redisAddr);
            config.useSingleServer().setDatabase(database);
            if (StrUtil.isNotBlank(password)) {
                config.useSingleServer().setPassword(password);
            }
            log.info("初始化[单体部署]方式 连接地址:" + address);
        } catch (Exception e) {
            log.error("初始化[单体部署]方式 异常：", e);
        }
        return config;
    }
}
