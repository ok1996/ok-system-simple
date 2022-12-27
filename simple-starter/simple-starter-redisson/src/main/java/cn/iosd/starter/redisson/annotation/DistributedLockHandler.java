package cn.iosd.starter.redisson.annotation;

import cn.iosd.starter.redisson.service.RedissonService;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;


/**
 * 注解解析器
 *
 * @author ok1996
 */
@Aspect
@Component
@Slf4j
@ConditionalOnProperty(prefix = "simple.redisson", name = "enabled", havingValue = "true")
public class DistributedLockHandler {

    @Resource
    RedissonService redissonService;

    public final static String LOCK_NAME_APPEND = "RedissonLock:";

    @Around("@annotation(distributedLock)")
    public Object around(ProceedingJoinPoint joinPoint, DistributedLock distributedLock) throws Throwable {
        log.info("[开始]执行RedisLock环绕通知,获取Redis分布式锁开始");
        //获取锁名称
        String lockName = distributedLock.value();
        lockName = LOCK_NAME_APPEND + lockName;

        int leaseTime = distributedLock.leaseTime();
        redissonService.lock(lockName, leaseTime);
        final Object proceed;
        try {
            log.info("获取Redis分布式锁[成功]，加锁完成，开始执行业务逻辑...");
            proceed = joinPoint.proceed();
        } catch (Throwable throwable) {
            throw throwable;
        } finally {
            //如果该线程还持有该锁，那么释放该锁。如果该线程不持有该锁，说明该线程的锁已到过期时间，自动释放锁
            if (redissonService.isHeldByCurrentThread(lockName)) {
                redissonService.unlock(lockName);
            }
            log.info("释放Redis分布式锁[成功]，解锁完成，结束业务逻辑...");
        }
        return proceed;
    }
}
