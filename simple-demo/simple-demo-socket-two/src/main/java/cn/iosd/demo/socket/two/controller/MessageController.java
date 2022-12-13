package cn.iosd.demo.socket.two.controller;

import cn.iosd.demo.socket.two.dto.ChatMessage;
import cn.iosd.starter.socket.service.SocketMessageServer;
import cn.iosd.starter.web.domain.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author ok1996
 */
@Tag(name = "消息管理")
@Slf4j
@RestController
@RequestMapping("message")
public class MessageController {

    @Autowired(required = false)
    private SocketMessageServer socketMessageServer;

    @Operation(summary = "广播所有连接客户端")
    @PostMapping("broadcast")
    public Response broadcast(@RequestBody Object chatMessage) {
        socketMessageServer.sendBroadcast("broadcastTest", chatMessage);
        return Response.ok();
    }

    @Operation(summary = "仅发送带群聊Room连接的客户端")
    @PostMapping("room")
    public Response room(@RequestBody ChatMessage chatMessage) {
        socketMessageServer.sendRoom(chatMessage.getEventName()
                , chatMessage.getRoom()
                , chatMessage.getMessage()
        );
        return Response.ok();
    }

    @Operation(summary = "发送带指定微服务连接的客户端")
    @PostMapping("service")
    public Response service(@RequestBody Object chatMessage) {
        socketMessageServer.sendService("serviceTest", chatMessage);
        return Response.ok();
    }
}
