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
     * 数据获取方式实现类
     *
     * @return
     */
    String source() default "localDictServiceImpl";

    /**
     * 关联字段，用于翻译字典项
     *
     * @return
     */
    String relatedField() default "";

    /**
     * 字典文件路径或接口地址
     *
     * @return
     */
    String path();
}
