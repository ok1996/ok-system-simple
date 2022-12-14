package cn.iosd.demo.grpc.client.service;

import cn.iosd.demo.grpc.proto.hello.HelloReply;
import cn.iosd.demo.grpc.proto.hello.HelloRequest;
import cn.iosd.demo.grpc.proto.hello.SimpleGrpc;
import cn.iosd.demo.grpc.proto.person.PersonHelloGrpc;
import cn.iosd.demo.grpc.proto.person.PersonHelloReply;
import cn.iosd.demo.grpc.proto.person.PersonHelloRequest;
import cn.iosd.starter.grpc.client.annotation.GrpcClient;
import org.springframework.stereotype.Service;

/**
 * @author ok1996
 */
@Service
public class GrpcDemoClientService {

    @GrpcClient(value = "grpc-server-hello")
    private SimpleGrpc.SimpleBlockingStub simpleStub;

    @GrpcClient(value = "grpc-server-person")
    private PersonHelloGrpc.PersonHelloBlockingStub personHelloBlockingStub;


    public String sendHelloMessage(final String name) {
        final HelloReply response = this.simpleStub.sayHello(HelloRequest.newBuilder().setName(name).build());
        return response.getMessage();
    }

    public String sendPersonMessage(final String name) {
        final PersonHelloReply response = this.personHelloBlockingStub.personSayHello(PersonHelloRequest.newBuilder().setName(name).build());
        return response.getMessage();
    }
}