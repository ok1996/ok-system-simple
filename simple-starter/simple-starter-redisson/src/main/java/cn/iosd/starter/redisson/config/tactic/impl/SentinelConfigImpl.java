package cn.iosd.starter.redisson.config.tactic.impl;

import cn.iosd.starter.redisson.properties.RedissonProperties;
import cn.iosd.starter.redisson.config.tactic.RedissonConfigService;
import org.apache.commons.lang3.StringUtils;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * 哨兵集群
 *
 * @author ok1996
 */
public class SentinelConfigImpl implements RedissonConfigService {
    private static final Logger log = LoggerFactory.getLogger(SentinelConfigImpl.class);

    @Override
    public Config createRedissonConfig(RedissonProperties properties) {
        Config config = new Config();
        List<String> sentinelAddresses = properties.getSentinelConfig().getSentinelAddresses();
        String password=properties.getSentinelConfig().getPassword();

        SentinelServersConfig sentinelConfig = config.useSentinelServers()
                .setMasterName(properties.getSentinelConfig().getSentinelMasterName())
                .setDatabase(properties.getSentinelConfig().getDatabase())
                .addSentinelAddress(sentinelAddresses.toArray(String[]::new));

        if (StringUtils.isNotBlank(password)) {
            sentinelConfig.setPassword(password);
        }

        log.info("初始化[哨兵部署]方式 连接地址: {}", sentinelAddresses);
        return config;
    }
}