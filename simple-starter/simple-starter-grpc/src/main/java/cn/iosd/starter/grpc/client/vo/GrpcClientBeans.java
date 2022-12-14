package cn.iosd.starter.grpc.client.vo;

import cn.iosd.starter.grpc.client.annotation.GrpcClient;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ok1996
 */
@Data
public class GrpcClientBeans {
    private final List<GrpcClientBean> injections = new ArrayList<>();


    public record GrpcClientBean(Object bean, GrpcClient client, Field field) {

    }

    public GrpcClientBeans add(final GrpcClientBean injection) {
        this.injections.add(injection);
        return this;
    }

}
