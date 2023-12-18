package cn.iosd.starter.redisson.service;

import cn.iosd.starter.redisson.manager.RedissonManager;
import org.redisson.Redisson;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;

import java.time.Duration;

/**
 * @author ok1996
 */
public class RedissonRateLimiterService {
    private final Redisson redisson;

    public RedissonRateLimiterService(RedissonManager redissonManager) {
        this.redisson = redissonManager.getRedisson();
    }

    public boolean tryAcquire(RateType type,String key, long rate, long rateTime, RateIntervalUnit timeUnit) {
        RRateLimiter rateLimiter = redisson.getRateLimiter(key);
        rateLimiter.trySetRate(type, rate, rateTime, timeUnit);
        rateLimiter.expireAsync(Duration.ofMillis(timeUnit.toMillis(rateTime)));
        return rateLimiter.tryAcquire();
    }

}
