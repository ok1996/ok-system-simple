package cn.iosd.starter.redisson.domain;

public class CacheName {
    /**
     * 缓存时间10分钟 Simple Redisson Cache: Expires Ten Minutes
     */
    public static final String EXPIRES_AFTER_TEN_MINUTES = "SimpleRCETM";
    /**
     * 缓存时间60分钟 Simple Redisson Cache: Expires One Hour
     */
    public static final String EXPIRES_AFTER_ONE_HOUR = "SimpleRCEOH";
    /**
     * 缓存不失效 Simple Redisson Cache: Expires Never
     */
    public static final String NEVER_EXPIRES = "SimpleRCEN";
}
