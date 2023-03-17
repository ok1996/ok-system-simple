package cn.iosd.starter.redisson.annotation;

import cn.iosd.starter.redisson.service.RedissonService;
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
 * 注解解析器
 *
 * @author ok1996
 */
@Aspect
@Component
@ConditionalOnProperty(prefix = "simple.redisson", name = "enabled", havingValue = "true")
public class DistributedLockHandler {
    private static final Logger log = LoggerFactory.getLogger(DistributedLockHandler.class);

    @Autowired
    RedissonService redissonService;

    public final static String LOCK_NAME_APPEND = "RedissonLock:";

    @Around("@annotation(distributedLock)")
    public Object around(ProceedingJoinPoint joinPoint, DistributedLock distributedLock) throws Throwable {
        String lockName = LOCK_NAME_APPEND + distributedLock.value();
        int leaseTime = distributedLock.leaseTime();

        log.info("[开始]执行RedisLock环绕通知,获取Redis分布式锁[{}]开始", lockName);
        RLock lock = redissonService.getLock(lockName);

        boolean isLocked = false;
        try {
            isLocked = lock.tryLock(leaseTime, TimeUnit.SECONDS);
            if (isLocked) {
                log.info("获取Redis分布式锁[{}]成功，加锁完成，开始执行业务逻辑...", lockName);
                return joinPoint.proceed();
            } else {
                throw new Exception("获取Redis分布式锁["+lockName+"]失败");
            }
        } finally {
            if (isLocked) {
                lock.unlock();
                log.info("释放Redis分布式锁[{}]成功，解锁完成，结束业务逻辑...", lockName);
            } else {
                log.info("Redis分布式锁[{}]未被获取，不需要进行解锁", lockName);
            }
        }
    }

}
