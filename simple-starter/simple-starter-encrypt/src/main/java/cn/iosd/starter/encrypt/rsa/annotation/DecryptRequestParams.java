package cn.iosd.starter.encrypt.rsa.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 对接口的请求参数解密
 * <p>
 * 注：支持RequestBody、URL查询参数
 * </p>
 * RequestBody 请求无需字段名称，URL查询参数请求默认字段encryptedData
 *
 * @author ok1996
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DecryptRequestParams {
}
