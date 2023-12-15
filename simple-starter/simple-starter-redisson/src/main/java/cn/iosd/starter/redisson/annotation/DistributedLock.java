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
    String METHOD_NAME = "DlMn";

    /**
     * 锁KEY值的固定部分
     */
    String value() default METHOD_NAME;

    /**
     * 锁KEY值的动态参数部分，支持el表达式
     */
    String param() default "";

    /**
     * 锁的有效时间-秒
     */
    int leaseTime() default 10;
}


