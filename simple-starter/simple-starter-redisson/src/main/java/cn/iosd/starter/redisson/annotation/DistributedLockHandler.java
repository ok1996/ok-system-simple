package cn.iosd.starter.redisson.annotation;

import cn.iosd.starter.redisson.service.RedissonLockService;
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

    public final static String LOCK_NAME_APPEND = "RLock:";

    @Around("@annotation(distributedLock)")
    public Object around(ProceedingJoinPoint joinPoint, DistributedLock distributedLock) throws Throwable {
        String lockName = LOCK_NAME_APPEND + distributedLock.value();
        int leaseTime = distributedLock.leaseTime();
        RLock lock = redissonLockService.getLock(lockName);
        boolean isLocked = false;
        log.debug("[开始]执行RedisLock环绕通知,获取Redis分布式锁[{}]开始", lockName);
        try {
            isLocked = lock.tryLock(leaseTime, TimeUnit.SECONDS);
            if (!isLocked) {
                throw new Exception("获取Redis分布式锁[" + lockName + "]失败");
            }
            log.info("获取Redis分布式锁[{}]成功，加锁完成，开始执行业务逻辑...", lockName);
            return joinPoint.proceed();
        } finally {
            if (isLocked) {
                lock.unlock();
                log.debug("释放Redis分布式锁[{}]成功，解锁完成，结束业务逻辑...", lockName);
            } else {
                log.debug("Redis分布式锁[{}]未被获取，不需要进行解锁", lockName);
            }
        }
    }


}
