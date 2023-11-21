package cn.iosd.starter.redisson.config.tactic.impl;


import cn.iosd.starter.redisson.properties.RedissonProperties;
import cn.iosd.starter.redisson.config.tactic.RedissonConfigService;
import org.apache.commons.lang3.StringUtils;
import org.redisson.config.Config;
import org.redisson.config.MasterSlaveServersConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 主从
 *
 * @author ok1996
 */
public class MasterSlaveConfigImpl implements RedissonConfigService {
    private static final Logger log = LoggerFactory.getLogger(MasterSlaveConfigImpl.class);

    @Override
    public Config createRedissonConfig(RedissonProperties properties) {
        Config config = new Config();
        List<String> slaveAddresses = properties.getMasterSlaveConfig().getSlaveAddresses();
        String masterAddress = properties.getMasterSlaveConfig().getMasterAddress();
        String password=properties.getMasterSlaveConfig().getPassword();

        MasterSlaveServersConfig masterSlaveConfig = config.useMasterSlaveServers()
                .setMasterAddress(masterAddress)
                .setDatabase(properties.getMasterSlaveConfig().getDatabase())
                .addSlaveAddress(slaveAddresses.toArray(String[]::new));

        if (StringUtils.isNotBlank(password)) {
            masterSlaveConfig.setPassword(password);
        }

        log.info("初始化[主从部署]方式 连接地址: 主节点 {}, 从节点 {}", masterAddress, slaveAddresses);
        return config;
    }
}