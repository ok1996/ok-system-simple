package cn.iosd.starter.redisson.annotation;

import cn.iosd.starter.redisson.service.RedissonLockService;
import cn.iosd.starter.redisson.utils.SpElUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.util.Map;
import java.util.function.Supplier;

/**
 * 注解解析器
 *
 * @author ok1996
 */
@Aspect
@Component
@ConditionalOnProperty(prefix = "simple.redisson", name = "enabled", havingValue = "true")
public class DistributedIdempotentHandler {
    private static final Logger log = LoggerFactory.getLogger(DistributedIdempotentHandler.class);

    private static final String LOCK_KEY_PREFIX = "RedissonIdempotent:";

    @Autowired
    RedissonLockService redissonLockService;

    /**
     * 接口幂等切面
     */
    @Around("@annotation(cn.iosd.starter.redisson.annotation.DistributedIdempotent)||" +
            "@within(cn.iosd.starter.redisson.annotation.DistributedIdempotent)")
    public Object idempotent(final ProceedingJoinPoint point) throws Throwable {
        log.info("[开始]执行DistributedIdempotent环绕通知");
        DistributedIdempotent idempotent = ((MethodSignature) point.getSignature()).getMethod().getAnnotation(DistributedIdempotent.class);
        String lockName = getLockName(SpElUtil.getArgMap(point), () -> DigestUtils.md5DigestAsHex(SpElUtil.getArgMap(point).toString().getBytes()), idempotent.value());
        RLock lock = redissonLockService.getLock(LOCK_KEY_PREFIX.concat(lockName));
        if (lock.isLocked()) {
            throw new Exception(idempotent.message());
        }
        if (lock.tryLock(idempotent.acquireTimeout(), idempotent.expireTime(), idempotent.unit())) {
            log.info("执行Redis幂等切面[成功]，加锁完成，开始执行业务逻辑...");
            try {
                Object proceed = point.proceed();
                log.info("[完成]执行DistributedIdempotent环绕通知");
                return proceed;
            } finally {
                lock.unlock();
            }
        }
        throw new Exception(idempotent.message());
    }

    private static String getLockName(final Map<String, Object> argMap, final Supplier<String> md5, final String spEl) {
        final String methodName = argMap.get(DistributedIdempotent.METHOD_NAME).toString();
        return spEl.equals(DistributedIdempotent.METHOD_NAME)
                ? methodName + md5.get()
                : SpElUtil.analytical(spEl, argMap, String.class, methodName) + md5.get();
    }
}
