package cn.iosd.starter.redisson.handler;

import cn.iosd.starter.redisson.annotation.DistributedRateLimiter;
import cn.iosd.starter.redisson.exception.RedissonException;
import cn.iosd.starter.redisson.service.RedissonRateLimiterService;
import cn.iosd.starter.redisson.utils.KeyUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(DistributedRateLimiterHandler.class);

    @Autowired
    RedissonRateLimiterService redissonRateLimiterService;

    @Around("@annotation(cn.iosd.starter.redisson.annotation.DistributedRateLimiter) || " +
            "@within(cn.iosd.starter.redisson.annotation.DistributedRateLimiter)")
    public Object handleRateLimiting(final ProceedingJoinPoint point) throws Throwable {
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        DistributedRateLimiter rateLimiter = method.getAnnotation(DistributedRateLimiter.class);
        String key = KeyUtil.generate(point, rateLimiter.value(), rateLimiter.param(), rateLimiter.includePointMd5());
        log.debug("DistributedLock，key[{}]", key);
        if (redissonRateLimiterService.tryAcquire(rateLimiter.type(),
                key, rateLimiter.rate(), rateLimiter.rateTime(), rateLimiter.timeUnit())) {
            return point.proceed();
        }
        throw new RedissonException(rateLimiter.message());
    }


}
