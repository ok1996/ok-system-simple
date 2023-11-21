package cn.iosd.starter.redisson.config.tactic.impl;

import cn.iosd.starter.redisson.properties.RedissonProperties;
import cn.iosd.starter.redisson.config.tactic.RedissonConfigService;
import org.apache.commons.lang3.StringUtils;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 集群
 *
 * @author ok1996
 */
public class ClusterConfigImpl implements RedissonConfigService {
    private static final Logger log = LoggerFactory.getLogger(ClusterConfigImpl.class);

    @Override
    public Config createRedissonConfig(RedissonProperties properties) {
        Config config = new Config();
        List<String> clusterAddresses = properties.getClusterConfig().getClusterAddresses();
        String password = properties.getClusterConfig().getPassword();

        ClusterServersConfig clusterServersConfig = config.useClusterServers()
                .addNodeAddress(clusterAddresses.toArray(String[]::new));

        if (StringUtils.isNotBlank(password)) {
            clusterServersConfig.setPassword(password);
        }

        log.info("初始化[集群部署]方式 连接地址: {}", clusterAddresses);
        return config;
    }
}