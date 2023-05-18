package cn.iosd.starter.grpc.server.interceptor;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.Status;

/**
 * 拦截：获取请求头参数
 *
 * @author ok1996
 */
public class CustomServerInterceptor implements ServerInterceptor {

    private ServiceCallStartHeaders serviceCallStartHeaders;

    public CustomServerInterceptor(ServiceCallStartHeaders serviceCallStartHeaders) {
        this.serviceCallStartHeaders = serviceCallStartHeaders;
    }

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> serverCall, Metadata headers, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        if (serviceCallStartHeaders != null) {
            Status status = serviceCallStartHeaders.verifyHeaders(headers);
            if (!status.isOk()) {
                throw status.asRuntimeException();
            }
        }
        return serverCallHandler.startCall(serverCall, headers);
    }
}
