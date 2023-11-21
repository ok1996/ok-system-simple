package cn.iosd.starter.redisson.service.impl;

import cn.iosd.starter.redisson.properties.RedissonProperties;
import cn.iosd.starter.redisson.service.RedissonConfigService;
import org.apache.commons.lang3.StringUtils;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
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
    public Config createRedissonConfig(RedissonProperties properties) {
        Config config = new Config();
        String password = properties.getStandaloneConfig().getPassword();
        SingleServerConfig serverConfig = config.useSingleServer()
                .setAddress(properties.getStandaloneConfig().getAddress())
                .setDatabase(properties.getStandaloneConfig().getDatabase());

        if (StringUtils.isNotBlank(password)) {
            serverConfig.setPassword(password);
        }

        log.info("初始化[单体部署]方式 连接地址: {}", properties.getStandaloneConfig().getAddress());
        return config;
    }
}
