package cn.iosd.starter.redisson.service;

import cn.iosd.starter.redisson.properties.RedissonProperties;
import cn.iosd.starter.redisson.service.impl.ClusterConfigImpl;
import cn.iosd.starter.redisson.service.impl.MasterSlaveConfigImpl;
import cn.iosd.starter.redisson.service.impl.SentinelConfigImpl;
import cn.iosd.starter.redisson.service.impl.StandaloneConfigImpl;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.config.Config;


/**
 * 初始化redisson实例
 *
 * @author ok1996
 */
@Slf4j
public class RedissonManager {

    private Config config;

    private Redisson redisson;

    public RedissonManager(RedissonProperties redissonProperties) {
        try {
            config = RedissonConfigFactory.getInstance().createConfig(redissonProperties);
            redisson = (Redisson) Redisson.create(config);
        } catch (Exception e) {
            log.error("Redisson init error", e);
            throw new IllegalArgumentException("please input correct configurations," +
                    "connectionType must in standalone/sentinel/cluster/masterslave");
        }
    }

    public Redisson getRedisson() {
        return redisson;
    }

    /**
     * Redisson连接方式配置工厂
     * 双重检查锁
     */
    static class RedissonConfigFactory {

        private RedissonConfigFactory() {
        }

        private static volatile RedissonConfigFactory factory = null;

        public static RedissonConfigFactory getInstance() {
            if (factory == null) {
                synchronized (Object.class) {
                    if (factory == null) {
                        factory = new RedissonConfigFactory();
                    }
                }
            }
            return factory;
        }


        /**
         * 根据连接类型获取对应连接方式的配置,基于策略模式
         *
         * @param redissonProperties redis连接信息
         * @return Config
         */
        Config createConfig(RedissonProperties redissonProperties) {

            String connectionType = redissonProperties.getType();
            //声明配置上下文
            RedissonConfigService redissonConfigService;
            switch (connectionType) {
                case "standalone":
                    redissonConfigService = new StandaloneConfigImpl();
                    break;
                case "sentinel":
                    redissonConfigService = new SentinelConfigImpl();
                    break;
                case "cluster":
                    redissonConfigService = new ClusterConfigImpl();
                    break;
                case "masterSlave":
                    redissonConfigService = new MasterSlaveConfigImpl();
                    break;
                default:
                    throw new IllegalArgumentException("创建Redisson连接Config失败！当前连接方式:" + connectionType);
            }
            return redissonConfigService.createRedissonConfig(redissonProperties);
        }
    }

}


