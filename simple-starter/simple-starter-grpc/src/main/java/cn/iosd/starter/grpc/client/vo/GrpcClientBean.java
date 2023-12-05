package cn.iosd.starter.grpc.client.vo;

import cn.iosd.starter.grpc.client.annotation.GrpcClient;

import java.lang.reflect.Field;

/**
 * @author ok1996
 */
public record GrpcClientBean(Object bean, GrpcClient client, Field field) {

}
