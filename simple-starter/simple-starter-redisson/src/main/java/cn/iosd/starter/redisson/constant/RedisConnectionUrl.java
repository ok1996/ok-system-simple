package cn.iosd.starter.redisson.constant;


/**
 * 拼接Redis-Url
 *
 * @author ok1996
 */
public enum RedisConnectionUrl {
    /**
     * Redis地址配置前缀
     */
    REDIS_CONNECTION_PREFIX("redis://", "Redis地址配置前缀");

    private final String value;
    private final String desc;

    RedisConnectionUrl(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
