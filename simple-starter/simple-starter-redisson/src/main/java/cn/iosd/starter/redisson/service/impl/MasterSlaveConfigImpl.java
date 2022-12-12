package cn.iosd.starter.redisson.service.impl;


import cn.hutool.core.util.StrUtil;
import cn.iosd.starter.redisson.constant.RedisConnectionUrl;
import cn.iosd.starter.redisson.properties.RedissonProperties;
import cn.iosd.starter.redisson.service.RedissonConfigService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.config.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * 主从
 *
 * @author ok1996
 */
@Slf4j
public class MasterSlaveConfigImpl implements RedissonConfigService {

    @Override
    public Config createRedissonConfig(RedissonProperties redissonProperties) {
        Config config = new Config();
        try {
            String address = redissonProperties.getAddress();
            String password = redissonProperties.getPassword();
            Integer database = redissonProperties.getDatabase();
            String[] addrTokens = address.split(",");
            String masterNodeAddr = addrTokens[0];
            //设置主节点ip
            config.useMasterSlaveServers().setMasterAddress(masterNodeAddr);
            if (StrUtil.isNotBlank(password)) {
                config.useMasterSlaveServers().setPassword(password);
            }
            config.useMasterSlaveServers().setDatabase(database);
            //设置从节点，移除第一个节点，默认第一个为主节点
            List<String> slaveList = new ArrayList<>();
            for (String addrToken : addrTokens) {
                slaveList.add(RedisConnectionUrl.REDIS_CONNECTION_PREFIX.getValue() + addrToken);
            }
            slaveList.remove(0);
            String[] applyIdStr = slaveList.toArray(new String[slaveList.size()]);
            config.useMasterSlaveServers().addSlaveAddress(applyIdStr);
            log.info("初始化[主从部署]方式 连接地址:" + address);
        } catch (Exception e) {
            log.error("初始化[主从部署]方式 异常：", e);
            e.printStackTrace();
        }
        return config;
    }

}
