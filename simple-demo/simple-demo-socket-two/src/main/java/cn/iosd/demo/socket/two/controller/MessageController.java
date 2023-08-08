package cn.iosd.demo.socket.two.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.iosd.demo.socket.two.dto.ChatMessage;
import cn.iosd.starter.socket.service.SocketMessageServer;
import cn.iosd.starter.web.domain.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


/**
 * @author ok1996
 */
@Tag(name = "消息管理")
@RestController
@RequestMapping("/simple-demo-socket-two/message")
public class MessageController {
    @Autowired(required = false)
    private SocketMessageServer socketMessageServer;

    @Operation(summary = "广播所有连接客户端")
    @GetMapping("broadcast")
    public Response<?> broadcast(String message) {
        socketMessageServer.sendBroadcast("broadcastTest", message);
        return Response.ok();
    }

    @Operation(summary = "仅发送带群聊Room连接的客户端")
    @GetMapping("room")
    public Response<?> room(@ParameterObject ChatMessage chatMessage) {
        socketMessageServer.sendRoom(chatMessage.getEventName()
                , chatMessage.getRoom()
                , chatMessage.getMessage()
        );
        return Response.ok();
    }

    @Operation(summary = "发送带指定微服务连接的客户端")
    @GetMapping("service")
    public Response<?> service(String message) {
        socketMessageServer.sendService("serviceTest", message);
        return Response.ok();
    }
}
