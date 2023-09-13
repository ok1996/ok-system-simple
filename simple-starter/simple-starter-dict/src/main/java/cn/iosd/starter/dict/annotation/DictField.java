package cn.iosd.starter.dict.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段对应字典转换
 *
 * @author ok1996
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DictField {

    /**
     * 关联字段，用于翻译字典项
     *
     * @return 关联字段名称
     */
    String relatedField() default "";


    /**
     * 接口实现类所需的参数类型
     *
     * @return 参数类型值
     */
    String dictionaryParams();

    /**
     * 字典获取方式实现类
     *
     * @return 实现类名称
     */
    String dictImplBeanName() default "";
}
