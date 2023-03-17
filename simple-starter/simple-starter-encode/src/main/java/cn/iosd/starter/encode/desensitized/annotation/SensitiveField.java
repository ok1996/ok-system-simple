package cn.iosd.starter.encode.desensitized.annotation;

import cn.iosd.starter.encode.desensitized.vo.SensitiveRule;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段对应脱敏规则
 *
 * @author ok1996
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface SensitiveField {
    /**
     * 脱敏规则
     *
     * @return
     */
    SensitiveRule rule();

    /**
     * 自定义规则-左侧几位字段进行操作
     *
     * @return
     */
    int prefixLen() default 0;

    /**
     * 自定义规则-右侧几位字段进行操作
     *
     * @return
     */
    int suffixLen() default 0;
}
