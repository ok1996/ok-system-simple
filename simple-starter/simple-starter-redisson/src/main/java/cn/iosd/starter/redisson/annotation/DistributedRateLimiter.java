package cn.iosd.starter.redisson.annotation;

import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 分布式限流器
 *
 * @author ok1996
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DistributedRateLimiter {

    String METHOD_NAME = "RaLi";

    /**
     * KEY值的固定部分
     */
    String value() default METHOD_NAME;

    /**
     * KEY值的动态参数部分，支持el表达式
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
     * 速率
     */
    long rate() default 100L;

    /**
     * 有效时间
     */
    long rateTime() default 1L;

    /**
     * 时间单位
     */
    RateIntervalUnit timeUnit() default RateIntervalUnit.SECONDS;

    /**
     * 失败提示信息
     */
    String message() default "访问频繁，请稍后重试!";

    /**
     * 限流类型 默认全局
     */
    RateType type() default RateType.OVERALL;
}
