package cn.iosd.starter.grpc.client.vo;

import cn.iosd.starter.grpc.client.interceptor.CustomClientInterceptor;
import cn.iosd.starter.grpc.client.interceptor.ClientCallStartHeaders;
import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author ok1996
 */
public class GrpcChannel {
    private static final ConcurrentHashMap<String, ManagedChannel> CHANNELS = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, Object> STUBS = new ConcurrentHashMap<>();

    public static ManagedChannel getChannel(String target, long timeout, ClientCallStartHeaders headers) {
        String key = target + timeout;
        CHANNELS.computeIfAbsent(key, k -> ManagedChannelBuilder.forTarget(target)
                .usePlaintext()
                .intercept(new CustomClientInterceptor(timeout, TimeUnit.MILLISECONDS, headers))
                .build());
        return CHANNELS.get(key);
    }

    public static <T> T getBlockingStub(ManagedChannel channel, Class<T> type) {
        if (channel == null) {
            throw new IllegalArgumentException("No channel");
        }

        String key = channel + type.getName();
        Object stub = STUBS.computeIfAbsent(key, k -> {
            try {
                Constructor<T> constructor = type.getDeclaredConstructor(Channel.class, CallOptions.class);
                constructor.setAccessible(true);
                return constructor.newInstance(channel, CallOptions.DEFAULT);
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                     InvocationTargetException e) {
                throw new RuntimeException("无法创建指定类型的Stub", e);
            }
        });

        return type.cast(stub);
    }

}

