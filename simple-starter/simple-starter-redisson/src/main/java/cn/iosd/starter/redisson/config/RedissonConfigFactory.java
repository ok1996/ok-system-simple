package cn.iosd.starter.redisson.config;

import cn.iosd.starter.redisson.properties.RedissonProperties;
import cn.iosd.starter.redisson.config.tactic.RedissonConfigService;
import cn.iosd.starter.redisson.config.tactic.impl.ClusterConfigImpl;
import cn.iosd.starter.redisson.config.tactic.impl.MasterSlaveConfigImpl;
import cn.iosd.starter.redisson.config.tactic.impl.SentinelConfigImpl;
import cn.iosd.starter.redisson.config.tactic.impl.StandaloneConfigImpl;
import org.redisson.config.Config;

/**
 * @author ok1996
 */
public class RedissonConfigFactory {
    /**
     * 静态内部类-单例模式
     */
    private static class RedissonConfigFactoryHolder {
        private static final RedissonConfigFactory INSTANCE = new RedissonConfigFactory();
    }

    public static RedissonConfigFactory getInstance() {
        return RedissonConfigFactoryHolder.INSTANCE;
    }

    public Config createConfig(RedissonProperties redissonProperties) {
        String connectionType = redissonProperties.getType();
        RedissonConfigService redissonConfigService = switch (connectionType) {
            case "standalone" -> new StandaloneConfigImpl();
            case "sentinel" -> new SentinelConfigImpl();
            case "cluster" -> new ClusterConfigImpl();
            case "masterSlave" -> new MasterSlaveConfigImpl();
            default -> throw new IllegalArgumentException("创建Redisson连接Config失败！当前连接方式:" + connectionType);
        };
        return redissonConfigService.createRedissonConfig(redissonProperties);
    }
}

