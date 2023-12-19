package cn.iosd.starter.redisson.handler;

import cn.iosd.starter.redisson.annotation.DistributedRateLimiter;
import cn.iosd.starter.redisson.exception.RedissonException;
import cn.iosd.starter.redisson.service.RedissonRateLimiterService;
import cn.iosd.starter.redisson.utils.LockUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RateType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author ok1996
 */
@Aspect
@Component
@ConditionalOnProperty(prefix = "simple.redisson", name = "enabled", havingValue = "true")
public class DistributedRateLimiterHandler {

    private static final String CLIENT_RATE_KEY_PREFIX = "RCrl:";
    private static final String GLOBAL_RATE_KEY_PREFIX = "RGrl:";

    @Autowired
    RedissonRateLimiterService redissonRateLimiterService;

    @Around("@annotation(cn.iosd.starter.redisson.annotation.DistributedRateLimiter) || " +
            "@within(cn.iosd.starter.redisson.annotation.DistributedRateLimiter)")
    public Object handleRateLimiting(final ProceedingJoinPoint point) throws Throwable {
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        DistributedRateLimiter rateLimiter = method.getAnnotation(DistributedRateLimiter.class);
        String lockName = LockUtil.generateLockName(point,
                rateLimiter.type() == RateType.OVERALL ? GLOBAL_RATE_KEY_PREFIX : CLIENT_RATE_KEY_PREFIX,
                rateLimiter.value(), rateLimiter.param());
        if (redissonRateLimiterService.tryAcquire(rateLimiter.type(),
                lockName, rateLimiter.rate(), rateLimiter.rateTime(), rateLimiter.timeUnit())) {
            return point.proceed();
        }
        throw new RedissonException(rateLimiter.message());
    }


}
