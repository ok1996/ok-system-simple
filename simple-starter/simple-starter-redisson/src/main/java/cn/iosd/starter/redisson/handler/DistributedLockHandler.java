package cn.iosd.starter.redisson.handler;

import cn.iosd.starter.redisson.annotation.DistributedLock;
import cn.iosd.starter.redisson.exception.RedissonException;
import cn.iosd.starter.redisson.service.RedissonLockService;
import cn.iosd.starter.redisson.utils.KeyUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author ok1996
 */
@Aspect
@Component
@ConditionalOnProperty(prefix = "simple.redisson", name = "enabled", havingValue = "true")
public class DistributedLockHandler {
    private static final Logger log = LoggerFactory.getLogger(DistributedLockHandler.class);

    @Autowired
    RedissonLockService redissonLockService;

    @Around("@annotation(distributedLock)")
    public Object around(ProceedingJoinPoint joinPoint, DistributedLock distributedLock) throws Throwable {
        String key = KeyUtil.generate(joinPoint, distributedLock.value(), distributedLock.param(), distributedLock.includePointMd5());
        int leaseTime = distributedLock.leaseTime();
        RLock lock = redissonLockService.getLock(key);

        log.debug("DistributedLock，key[{}]", key);
        try {
            if (!lock.tryLock(leaseTime, TimeUnit.SECONDS)) {
                throw new RedissonException("获取Redis分布式锁[" + key + "]失败");
            }
            return joinPoint.proceed();
        } catch (InterruptedException e) {
            throw new RedissonException("Redis分布式加锁[" + key + "]失败", e);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}
