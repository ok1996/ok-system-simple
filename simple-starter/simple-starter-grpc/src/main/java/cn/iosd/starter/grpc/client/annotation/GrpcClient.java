package cn.iosd.starter.grpc.client.annotation;

import org.springframework.context.annotation.Bean;

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
@Bean
public @interface GrpcClient {

    /**
     * 获取 simple.grpc.client.channel.{value}.address 配置项
     *
     * @return {value}值
     */
    String value();

    /**
     * 设置Grpc调用超时时间-单位毫秒，默认5000毫秒
     * <p>
     * 优先级高于配置项 simple.grpc.client.timeout
     *
     * @return 超时时间
     */
    long timeout() default -1;

    /**
     * Grpc设置请求头实现类名称，当实现类只有一个时可为空
     *
     * @return 实现类名称
     */
    String headerBeanName() default "";
}
