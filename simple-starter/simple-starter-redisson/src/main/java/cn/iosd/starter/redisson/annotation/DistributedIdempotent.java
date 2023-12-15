package cn.iosd.starter.redisson.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 分布式幂等
 *
 * @author ok1996
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DistributedIdempotent {

    String METHOD_NAME = "DiMn";

    /**
     * 锁KEY值的固定部分
     */
    String value() default METHOD_NAME;

    /**
     * 锁KEY值的动态参数部分，支持el表达式
     *
     * <pre>
     *  1.获取方法名称
     *      #methodName
     *  2.获取方法参数中的某个值
     *      void test(String id)  => #id
     * </pre>
     */
    String param() default "";

    /**
     * 获取锁失败后的提示信息
     *
     * @return
     */
    String message() default "操作频繁，请稍后重试!";

    /**
     * 获取锁的最长等待时间
     */
    long acquireTimeout() default 0;

    /**
     * 获取后持有锁的最长时间，如果它尚未通过调用unlock 。 如果leaseTime 为-1，则保持锁定直到明确解锁。
     */
    long expireTime() default 10;

    /**
     * 时间单位，默认为秒
     *
     * @return {@link TimeUnit}
     */
    TimeUnit unit() default TimeUnit.SECONDS;

    /**
     * 是否在业务执行结束后解锁,默认true
     *
     * @return false：则等待expireTime自动过期后自动解锁
     * <br/>
     * true：业务执行结束point.proceed()后解锁
     */
    boolean executionFinishedUnlock() default true;
}
