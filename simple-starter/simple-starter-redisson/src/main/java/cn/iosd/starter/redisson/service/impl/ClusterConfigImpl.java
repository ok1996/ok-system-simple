package cn.iosd.starter.redisson.service.impl;

import cn.iosd.starter.redisson.properties.RedissonProperties;
import cn.iosd.starter.redisson.service.RedissonConfigService;
import org.apache.commons.lang3.StringUtils;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 集群
 *
 * @author ok1996
 */
public class ClusterConfigImpl implements RedissonConfigService {
    private static final Logger log = LoggerFactory.getLogger(ClusterConfigImpl.class);

    @Override
    public Config createRedissonConfig(RedissonProperties redissonProperties) {
        Config config = new Config();
        String address = redissonProperties.getAddress();
        String password = redissonProperties.getPassword();
        String[] addrTokens = address.split(",");
        for (String addrToken : addrTokens) {
            config.useClusterServers().addNodeAddress(
                    RedissonProperties.REDIS_CONNECTION_PREFIX + addrToken
            );
        }
        if (StringUtils.isNotBlank(password)) {
            config.useClusterServers().setPassword(password);
        }
        log.info("初始化[集群部署]方式 连接地址:{}", address);
        return config;
    }

}
