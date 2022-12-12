package cn.iosd.starter.redisson.service.impl;


import cn.hutool.core.util.StrUtil;
import cn.iosd.starter.redisson.constant.RedisConnectionUrl;
import cn.iosd.starter.redisson.properties.RedissonProperties;
import cn.iosd.starter.redisson.service.RedissonConfigService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.config.Config;

/**
 * 集群
 *
 * @author ok1996
 */
@Slf4j
public class ClusterConfigImpl implements RedissonConfigService {

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
                if (StrUtil.isNotBlank(password)) {
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
