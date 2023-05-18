package cn.iosd.starter.grpc.client.interceptor;

import java.util.Map;

/**
 * 客户端增加请求头
 *
 * @author ok1996
 */
public interface ClientCallStartHeaders {
    /**
     * 拦截调用Grpc增加请求头
     *
     * @return 请求头Key-Value
     */
    Map<String, String> headers();
}
