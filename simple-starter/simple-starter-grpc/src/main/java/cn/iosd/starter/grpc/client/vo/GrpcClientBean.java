package cn.iosd.starter.grpc.client.vo;

import cn.iosd.starter.grpc.client.annotation.GrpcClient;

import java.lang.reflect.Field;

public class GrpcClientBean {
    private Object bean;
    private GrpcClient client;
    private Field field;

    public GrpcClientBean(Object bean, GrpcClient client, Field field) {
        this.bean = bean;
        this.client = client;
        this.field = field;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public GrpcClient getClient() {
        return client;
    }

    public void setClient(GrpcClient client) {
        this.client = client;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }
}
