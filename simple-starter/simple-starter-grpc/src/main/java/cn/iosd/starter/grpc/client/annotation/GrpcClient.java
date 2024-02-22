package cn.iosd.starter.grpc.client.annotation;

import cn.iosd.starter.grpc.client.interceptor.ClientCallStartHeaders;
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
     * 设置 gRPC 调用超时时间-单位毫秒，若为-1则从配置项获取
     * <p>
     * 优先级高于配置项 simple.grpc.client.timeout 默认5000毫秒
     *
     * @return 超时时间
     */
    long timeout() default -1;

    /**
     * 用于配置 gRPC 请求头的实现类
     * <p>
     * 若只有一个实现类可用，此字段不填默认取其实现类
     * <p>
     * 若没有可用的实现类，将其留空则不传
     *
     * @return 请求头实现类
     */
    Class<? extends ClientCallStartHeaders> headerClass() default ClientCallStartHeaders.class;
}
