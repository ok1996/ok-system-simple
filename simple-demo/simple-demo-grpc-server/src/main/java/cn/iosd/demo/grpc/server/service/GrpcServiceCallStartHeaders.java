package cn.iosd.demo.grpc.server.service;

import cn.iosd.starter.grpc.server.interceptor.ServiceCallStartHeaders;
import io.grpc.Metadata;
import io.grpc.Status;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author ok1996
 */
@Component
public class GrpcServiceCallStartHeaders implements ServiceCallStartHeaders {
    @Override
    public Status verifyHeaders(Metadata headers) {
        String value = headers.get(Metadata.Key.of("token", Metadata.ASCII_STRING_MARSHALLER));
        if (StringUtils.isEmpty(value)) {
            return Status.UNAUTHENTICATED.withDescription("请求头参数token值为空");
        }
        return Status.OK;
    }
}
