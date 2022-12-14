package cn.iosd.starter.grpc.client.vo;

import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.lang.reflect.Constructor;

/**
 * @author ok1996
 */
public class GrpcChannel {
    private ManagedChannel channel;

    public GrpcChannel(String target) {
        channel = ManagedChannelBuilder.forTarget(target)
                .usePlaintext()
                .build();
    }

    public Object getBlockingStub(Class<?> type) {
        try {
            Constructor c = type.getDeclaredConstructor(new Class[]{Channel.class, CallOptions.class});
            c.setAccessible(true);
            return c.newInstance(new Object[]{channel, CallOptions.DEFAULT});
        } catch (Exception e) {
            throw new RuntimeException("service接口不存在参数为Channel的构造函数");
        }
    }

}
