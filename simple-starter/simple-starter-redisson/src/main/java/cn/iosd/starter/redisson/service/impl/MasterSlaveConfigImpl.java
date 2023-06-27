package cn.iosd.starter.redisson.service.impl;


import cn.iosd.starter.redisson.properties.RedissonProperties;
import cn.iosd.starter.redisson.service.RedissonConfigService;
import org.apache.commons.lang3.StringUtils;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 主从
 *
 * @author ok1996
 */
public class MasterSlaveConfigImpl implements RedissonConfigService {
    private static final Logger log = LoggerFactory.getLogger(MasterSlaveConfigImpl.class);

    @Override
    public Config createRedissonConfig(RedissonProperties redissonProperties) {
        Config config = new Config();

        String address = redissonProperties.getAddress();
        String password = redissonProperties.getPassword();
        Integer database = redissonProperties.getDatabase();
        String[] addrTokens = address.split(",");
        String masterNodeAddr = addrTokens[0];
        //设置主节点ip
        config.useMasterSlaveServers().setMasterAddress(masterNodeAddr);
        if (StringUtils.isNotBlank(password)) {
            config.useMasterSlaveServers().setPassword(password);
        }
        config.useMasterSlaveServers().setDatabase(database);
        //设置从节点，移除第一个节点，默认第一个为主节点
        List<String> slaveList = new ArrayList<>();
        for (String addrToken : addrTokens) {
            slaveList.add(RedissonProperties.REDIS_CONNECTION_PREFIX + addrToken);
        }
        slaveList.remove(0);
        String[] applyIdStr = slaveList.toArray(new String[slaveList.size()]);
        config.useMasterSlaveServers().addSlaveAddress(applyIdStr);
        log.info("初始化[主从部署]方式 连接地址:" + address);
        return config;
    }

}
