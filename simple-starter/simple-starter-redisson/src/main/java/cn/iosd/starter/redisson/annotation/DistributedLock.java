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
     * Lock Constant
     */
    String CONSTANT = "IC";

    /**
     * key固定值
     */
    String value() default CONSTANT;

    /**
     * key动态参数值，支持el表达式
     *
     * <pre>
     *  1.获取方法参数中的某个值
     *      void test(String id)  => #id
     *  2.获取对象参数中的某个值
     *      void test(Vo vo)  => #vo.id
     *  3.参数值获取不到，默认为：DV
     *      void test(String id)  => #im
     *  4.默认空，不拼接到key中
     * </pre>
     */
    String param() default "";

    /**
     * key后缀
     * <pre>
     * 是否拼接MD5
     *   值：从切点获取方法参数和实例字符串生成对象，转为md5
     * </pre>
     *
     * @return 默认生成Md5后缀
     */
    boolean includePointMd5() default true;

    /**
     * 锁的有效时间-秒
     */
    int leaseTime() default 10;
}


