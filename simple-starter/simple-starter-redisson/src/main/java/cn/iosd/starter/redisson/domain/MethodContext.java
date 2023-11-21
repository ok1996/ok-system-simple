package cn.iosd.starter.redisson.domain;

import java.util.Map;


/**
 * MethodContext 类用于封装方法调用的上下文信息。
 * 这个类的主要目的是将方法参数和方法名称分离，便于理解和使用
 *
 * @author ok1996
 */
public record MethodContext(Map<String, Object> argMap, String methodName) {
}
