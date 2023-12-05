package cn.iosd.starter.grpc.client.vo;

import cn.iosd.starter.grpc.client.annotation.GrpcClient;

import java.lang.reflect.Field;

/**
 * GrpcClient相关信息的数据结构
 *
 * @author ok1996
 */
public record GrpcClientBean(Object bean, GrpcClient client, Field field) {

}
