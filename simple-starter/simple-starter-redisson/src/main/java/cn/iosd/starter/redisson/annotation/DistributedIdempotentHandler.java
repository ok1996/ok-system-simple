package cn.iosd.starter.redisson.annotation;

import cn.iosd.starter.redisson.domain.MethodContext;
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

import java.util.function.Supplier;

/**
 * @author ok1996
 */
@Aspect
@Component
@ConditionalOnProperty(prefix = "simple.redisson", name = "enabled", havingValue = "true")
public class DistributedIdempotentHandler {
    private static final Logger log = LoggerFactory.getLogger(DistributedIdempotentHandler.class);

    private static final String LOCK_KEY_PREFIX = "RIdem:";

    @Autowired
    RedissonLockService redissonLockService;

    /**
     * 接口幂等切面
     */
    @Around("@annotation(cn.iosd.starter.redisson.annotation.DistributedIdempotent)||" +
            "@within(cn.iosd.starter.redisson.annotation.DistributedIdempotent)")
    public Object idempotent(final ProceedingJoinPoint point) throws Throwable {
        DistributedIdempotent idempotent = ((MethodSignature) point.getSignature()).getMethod().getAnnotation(DistributedIdempotent.class);
        String lockName = getLockName(SpElUtil.getArgMap(point)
                , idempotent.value()
                , () -> DigestUtils.md5DigestAsHex(SpElUtil.getArgMap(point).toString().getBytes()));
        RLock lock = redissonLockService.getLock(LOCK_KEY_PREFIX.concat(lockName));

        log.debug("[开始]执行DistributedIdempotent环绕通知，锁[{}]", lockName);
        if (lock.tryLock(idempotent.acquireTimeout(), idempotent.expireTime(), idempotent.unit())) {
            try {
                return point.proceed();
            } finally {
                if (idempotent.executionFinishedUnlock()) {
                    lock.unlock();
                    log.debug("[完成]执行DistributedIdempotent环绕通知，锁[{}]", lockName);
                }
            }
        }
        throw new Exception(idempotent.message());
    }

    /**
     * 根据传入的参数和表达式生成锁的名称
     * <p/>
     * 如果value等于METHOD_NAME常量，那么直接使用方法名称和MD5生成锁名称
     * <p/>
     * 否则，使用SpEL表达式分析得到的结果和MD5生成锁名称
     * <p/>
     *
     * @param context 包含方法的参数和名称
     * @param value   自定义锁名称-前缀，支持SpEL表达式，用于动态生成锁的名称
     * @param md5     一个提供MD5哈希的Supplier，用于生成锁名称的后缀
     * @return 生成的完整的锁名称
     */
    private static String getLockName(final MethodContext context, final String value, final Supplier<String> md5) {
        return value.equals(DistributedIdempotent.METHOD_NAME)
                ? context.methodName() + md5.get()
                : SpElUtil.analytical(value, context.argMap(), String.class, context.methodName()) + md5.get();
    }

}
