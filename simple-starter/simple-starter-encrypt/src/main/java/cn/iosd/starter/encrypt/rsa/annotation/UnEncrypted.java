package cn.iosd.starter.encrypt.rsa.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 该注解用于标记一个控制器方法或接口，表明该方法不受全局参数解密和返回参数加密的影响
 * <br/>
 * 被 {@code @UnEncrypted} 注解标记的方法将不会对其请求参数进行解密和返回参数进行加密
 *
 * @author ok1996
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface UnEncrypted {
}
