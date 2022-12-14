package cn.iosd.starter.grpc.server.annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Grpc服务端自定义注解
 * <p>
 * 扫描使用GrpcService注解的Bean->添加到io.grpc.ServerBuilder
 * </p>
 *
 * @author ok1996
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
@Bean
public @interface GrpcService {
}
