package cn.iosd.starter.encode.rsa.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 复合式注解
 * </p>
 * 对接口的请求参数解密和对接口返回参数加密，以保证接口的安全性
 *
 * @author ok1996
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@DecryptRequestParams
@EncryptResponseParams
public @interface SecureParams {
}
