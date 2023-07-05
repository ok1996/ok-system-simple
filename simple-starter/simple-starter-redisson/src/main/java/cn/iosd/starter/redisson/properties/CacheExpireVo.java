package cn.iosd.starter.redisson.properties;

/**
 * @author ok1996
 */
public class CacheExpireVo {
    private Long ttl = -1L;

    private Long maxIdleTime = -1L;

    public Long getTtl() {
        return ttl;
    }

    public void setTtl(Long ttl) {
        this.ttl = ttl;
    }

    public Long getMaxIdleTime() {
        return maxIdleTime;
    }

    public void setMaxIdleTime(Long maxIdleTime) {
        this.maxIdleTime = maxIdleTime;
    }
}
