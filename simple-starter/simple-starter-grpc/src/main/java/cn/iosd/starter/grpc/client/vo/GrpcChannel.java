package cn.iosd.starter.grpc.client.vo;

import cn.iosd.starter.grpc.client.interceptor.DeadlineInterceptor;
import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

/**
 * @author ok1996
 */
public class GrpcChannel {
    private ManagedChannel channel;

    public GrpcChannel(String target, long timeout) {
        channel = ManagedChannelBuilder.forTarget(target)
                .usePlaintext()
                .intercept(new DeadlineInterceptor(timeout, TimeUnit.MILLISECONDS))
                .build();
    }

    public <T> T getBlockingStub(Class<T> type) {
        try {
            Constructor<T> constructor = type.getDeclaredConstructor(Channel.class, CallOptions.class);
            constructor.setAccessible(true);
            return constructor.newInstance(channel, CallOptions.DEFAULT);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new RuntimeException("无法创建指定类型的Stub", e);
        }
    }


}
