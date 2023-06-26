package cn.iosd.starter.redisson.service.impl;

import cn.iosd.starter.redisson.properties.RedissonProperties;
import cn.iosd.starter.redisson.service.RedissonConfigService;
import org.apache.commons.lang3.StringUtils;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 哨兵集群
 *
 * @author ok1996
 */
public class SentinelConfigImpl implements RedissonConfigService {
    private static final Logger log = LoggerFactory.getLogger(SentinelConfigImpl.class);

    @Override
    public Config createRedissonConfig(RedissonProperties redissonProperties) {
        Config config = new Config();
        try {
            String address = redissonProperties.getAddress();
            String password = redissonProperties.getPassword();
            Integer database = redissonProperties.getDatabase();
            String[] addrTokens = address.split(",");
            String sentinelAliasName = addrTokens[0];
            //设置redis配置文件sentinel.conf配置的sentinel别名
            config.useSentinelServers().setMasterName(sentinelAliasName);
            config.useSentinelServers().setDatabase(database);
            if (StringUtils.isNotBlank(password)) {
                config.useSentinelServers().setPassword(password);
            }
            //设置sentinel节点的服务IP和端口
            for (int i = 1; i < addrTokens.length; i++) {
                config.useSentinelServers().addSentinelAddress(RedissonProperties.REDIS_CONNECTION_PREFIX + addrTokens[i]);
            }
            log.info("初始化[哨兵部署]方式 连接地址:" + address);
        } catch (Exception e) {
            log.error("初始化[哨兵部署]方式 异常：", e);

        }
        return config;
    }
}
