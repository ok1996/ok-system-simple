package cn.iosd.demo.grpc.server.service;


import cn.iosd.starter.grpc.server.annotation.GrpcService;
import cn.iosd.demo.grpc.proto.hello.HelloReply;
import cn.iosd.demo.grpc.proto.hello.HelloRequest;
import cn.iosd.demo.grpc.proto.hello.SimpleGrpc;
import io.grpc.stub.StreamObserver;

/**
 * @author ok1996
 */
@GrpcService
public class GrpcHelloService extends SimpleGrpc.SimpleImplBase {

    @Override
    public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
        HelloReply reply = HelloReply.newBuilder().setMessage("GrpcHelloService Hello ==> " + req.getName()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

}

