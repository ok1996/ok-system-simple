package cn.iosd.starter.grpc.client.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Grpc客户端自定义注解
 * <p>
 * 注释BlockingStub，自动创建GrpcChannel注入
 *
 * @author ok1996
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface GrpcClient {

    /**
     * 获取 simple.grpc.client.channel.{value}.address 配置项
     *
     * @return
     */
    String value();
}
