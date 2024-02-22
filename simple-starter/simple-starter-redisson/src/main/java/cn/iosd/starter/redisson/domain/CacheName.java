package cn.iosd.starter.redisson.domain;

public class CacheName {
    /**
     * 缓存时间10分钟
     */
    public static final String EXPIRES_AFTER_TEN_MINUTES = "cacheExpiresAfterTenMinutes";
    /**
     * 缓存时间60分钟
     */
    public static final String EXPIRES_AFTER_ONE_HOUR = "cacheExpiresAfterOneHour";
    /**
     * 缓存不失效
     */
    public static final String NEVER_EXPIRES = "cacheNeverExpires";
}
