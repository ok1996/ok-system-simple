package cn.iosd.starter.grpc.client.interceptor;

import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.ForwardingClientCall;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 拦截：设置Grpc调用超时时间、请求头参数
 *
 * @author ok1996
 */
public class CustomClientInterceptor implements ClientInterceptor {
    private final long timeout;
    private final TimeUnit timeUnit;

    private final ClientCallStartHeaders clientCallStartHeaders;

    public CustomClientInterceptor(long timeout, TimeUnit timeUnit, ClientCallStartHeaders headers) {
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.clientCallStartHeaders = headers;
    }

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
            MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel channel) {
        CallOptions newCallOptions = callOptions.withDeadlineAfter(timeout, timeUnit);
        if (clientCallStartHeaders == null || clientCallStartHeaders.headers().isEmpty()) {
            return channel.newCall(method, newCallOptions);
        }
        return new ForwardingClientCall.SimpleForwardingClientCall<>(channel.newCall(method, newCallOptions)) {
            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                // 在请求开始前设置头部信息
                Map<String, String> map = clientCallStartHeaders.headers();
                for (String key : map.keySet()) {
                    String value = map.get(key);
                    headers.put(Metadata.Key.of(key, Metadata.ASCII_STRING_MARSHALLER), value);
                }
                super.start(responseListener, headers);
            }
        };
    }
}
