package cn.iosd.demo.socketio.one.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.iosd.demo.socketio.one.dto.ChatMessage;
import cn.iosd.starter.socketio.service.SocketIOMessageServer;
import cn.iosd.starter.web.domain.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author ok1996
 */
@Tag(name = "消息管理")
@RestController
@RequestMapping("/simple-demo-socketio-one/message")
public class MessageController {

    @Autowired(required = false)
    private SocketIOMessageServer socketIOMessageServer;

    @Operation(summary = "广播所有连接客户端")
    @GetMapping("broadcast")
    public Response<?> broadcast(String message) {
        socketIOMessageServer.sendBroadcast("broadcastTest", message);
        return Response.ok();
    }

    @Operation(summary = "仅发送带群聊Room连接的客户端")
    @GetMapping("room")
    public Response<?> room(@ParameterObject ChatMessage chatMessage) {
        socketIOMessageServer.sendRoom(chatMessage.getEventName()
                , chatMessage.getRoom()
                , chatMessage.getMessage()
        );
        return Response.ok();
    }

    @Operation(summary = "发送带指定微服务连接的客户端")
    @GetMapping("service")
    public Response<?> service(String message) {
        socketIOMessageServer.sendService("serviceTest", message);
        return Response.ok();
    }
}
