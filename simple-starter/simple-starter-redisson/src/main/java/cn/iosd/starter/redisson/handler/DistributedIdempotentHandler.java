package cn.iosd.starter.redisson.handler;

import cn.iosd.starter.redisson.annotation.DistributedIdempotent;
import cn.iosd.starter.redisson.exception.RedissonException;
import cn.iosd.starter.redisson.service.RedissonLockService;
import cn.iosd.starter.redisson.utils.KeyUtil;
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

/**
 * @author ok1996
 */
@Aspect
@Component
@ConditionalOnProperty(prefix = "simple.redisson", name = "enabled", havingValue = "true")
public class DistributedIdempotentHandler {
    private static final Logger log = LoggerFactory.getLogger(DistributedIdempotentHandler.class);

    @Autowired
    RedissonLockService redissonLockService;

    @Around("@annotation(cn.iosd.starter.redisson.annotation.DistributedIdempotent)||" +
            "@within(cn.iosd.starter.redisson.annotation.DistributedIdempotent)")
    public Object idempotent(final ProceedingJoinPoint point) throws Throwable {
        DistributedIdempotent idempotent = ((MethodSignature) point.getSignature()).getMethod().getAnnotation(DistributedIdempotent.class);
        String key = KeyUtil.generate(point, idempotent.value(), idempotent.param(), idempotent.includePointMd5());
        RLock lock = redissonLockService.getLock(key);

        log.debug("DistributedIdempotent，key[{}]", key);
        if (!lock.isLocked() && lock.tryLock(idempotent.acquireTimeout(), idempotent.expireTime(), idempotent.unit())) {
            try {
                return point.proceed();
            } finally {
                if (idempotent.executionFinishedUnlock()) {
                    lock.unlock();
                }
            }
        }
        throw new RedissonException(idempotent.message());
    }
}
