package cn.iosd.starter.redisson.service.impl;

import cn.iosd.starter.redisson.constant.RedisConnectionUrl;
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
        try {
            String address = redissonProperties.getAddress();
            String password = redissonProperties.getPassword();
            String[] addrTokens = address.split(",");
            //设置cluster节点的服务IP和端口
            for (int i = 0; i < addrTokens.length; i++) {
                config.useClusterServers()
                        .addNodeAddress(RedisConnectionUrl.REDIS_CONNECTION_PREFIX.getValue()
                                + addrTokens[i]);
                if (StringUtils.isNotBlank(password)) {
                    config.useClusterServers().setPassword(password);
                }
            }
            log.info("初始化[集群部署]方式 连接地址:" + address);
        } catch (Exception e) {
            log.error("初始化[集群部署]方式 异常：", e);
            e.printStackTrace();
        }
        return config;
    }
}
