package cn.iosd.starter.redisson.handler;

import cn.iosd.starter.redisson.annotation.DistributedLock;
import cn.iosd.starter.redisson.service.RedissonLockService;
import cn.iosd.starter.redisson.utils.LockUtil;
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

    private static final String LOCK_KEY_PREFIX = "RLock:";

    @Around("@annotation(distributedLock)")
    public Object around(ProceedingJoinPoint joinPoint, DistributedLock distributedLock) throws Throwable {
        String lockName = LockUtil.generateLockName(joinPoint, LOCK_KEY_PREFIX, distributedLock.value(), distributedLock.param());
        int leaseTime = distributedLock.leaseTime();
        RLock lock = redissonLockService.getLock(lockName);

        log.debug("[开始]尝试获取Redis分布式锁[{}]", lockName);
        try {
            if (!lock.tryLock(leaseTime, TimeUnit.SECONDS)) {
                throw new RuntimeException("获取Redis分布式锁[" + lockName + "]失败");
            }
            return joinPoint.proceed();
        } catch (InterruptedException e) {
            throw new RuntimeException("Redis分布式加锁[" + lockName + "]失败", e);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
                log.debug("释放Redis分布式锁[{}]成功", lockName);
            }
        }
    }
}
