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


    @Data
    public static class GrpcClientBean {

        private final Object bean;

        private final GrpcClient client;

        private final Field field;

        public GrpcClientBean(final Object bean, final GrpcClient client, final Field field) {
            this.bean = bean;
            this.client = client;
            this.field = field;
        }
    }

    public GrpcClientBeans add(final GrpcClientBean injection) {
        this.injections.add(injection);
        return this;
    }

}
