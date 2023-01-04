package cn.iosd.starter.redisson.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 分布式锁
 *
 * @author ok1996
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DistributedLock {
    /**
     * 锁的名称
     */
    String value() default "RedissonLock";

    /**
     * 锁的有效时间-秒
     */
    int leaseTime() default 10;
}


