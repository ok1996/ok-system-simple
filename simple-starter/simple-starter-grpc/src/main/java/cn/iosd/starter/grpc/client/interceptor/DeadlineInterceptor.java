package cn.iosd.starter.grpc.client.interceptor;

import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.MethodDescriptor;

import java.util.concurrent.TimeUnit;

/**
 * 拦截：设置Grpc调用超时时间
 *
 * @author ok1996
 */
public class DeadlineInterceptor implements ClientInterceptor {
    private final long timeout;
    private final TimeUnit timeUnit;

    public DeadlineInterceptor(long timeout, TimeUnit timeUnit) {
        this.timeout = timeout;
        this.timeUnit = timeUnit;
    }

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
            MethodDescriptor<ReqT, RespT> methodDescriptor,
            CallOptions callOptions,
            Channel channel) {
        CallOptions newCallOptions = callOptions.withDeadlineAfter(timeout, timeUnit);
        return channel.newCall(methodDescriptor, newCallOptions);
    }
}
