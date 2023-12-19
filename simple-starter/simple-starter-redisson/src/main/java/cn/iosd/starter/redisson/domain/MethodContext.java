package cn.iosd.starter.redisson.domain;

import java.util.Map;


/**
 * MethodContext 类用于封装方法调用的上下文信息
 *
 * @author ok1996
 */
public record MethodContext(Map<String, Object> argMap, String name) {
}
