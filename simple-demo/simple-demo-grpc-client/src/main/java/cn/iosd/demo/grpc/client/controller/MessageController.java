package cn.iosd.demo.grpc.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.iosd.demo.grpc.client.service.GrpcDemoClientService;
import cn.iosd.starter.web.domain.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author ok1996
 */
@Tag(name = "客户端调用服务端")
@RestController
@RequestMapping("/simple-demo-grpc-client/message")
public class MessageController {

    @Autowired
    private GrpcDemoClientService service;

    @Operation(summary = "往Hello服务端发送消息")
    @GetMapping("/sendHello")
    public Response<String> sendHello(String message) {
        return Response.ok(service.sendHelloMessage(message));
    }

    @Operation(summary = "往Person服务端发送消息")
    @GetMapping("/sendPerson")
    public Response<String> sendPerson(String message) {
        return Response.ok(service.sendPersonMessage(message));
    }

}
