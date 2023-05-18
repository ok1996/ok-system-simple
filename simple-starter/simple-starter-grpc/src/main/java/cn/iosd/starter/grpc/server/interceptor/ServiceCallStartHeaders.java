package cn.iosd.starter.grpc.server.interceptor;


import io.grpc.Metadata;
import io.grpc.Status;

/**
 * 服务端校验请求头
 *
 * @author ok1996
 */
public interface ServiceCallStartHeaders {
    /**
     * 校验请求头方法
     *
     * @param headers 请求头
     * @return 校验实体
     */
    Status verifyHeaders(Metadata headers);
}
