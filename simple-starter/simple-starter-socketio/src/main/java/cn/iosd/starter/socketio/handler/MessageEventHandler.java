package cn.iosd.starter.socketio.handler;

import cn.iosd.starter.socketio.constant.SocketIOConstants;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 消息事件处理器
 *
 * @author ok1996
 */
@Component
public class MessageEventHandler {
    private static final Logger log = LoggerFactory.getLogger(MessageEventHandler.class);

    /**
     * 添加connect事件，当客户端发起连接时调用
     *
     * @param client 客户端
     */
    @OnConnect
    public void onConnect(SocketIOClient client) {
        if (client == null) {
            log.error("客户端建立连接失败: SocketIOClient为空");
            return;
        }
        String sessionId = client.getSessionId().toString();
        joinRoom(client, client.getHandshakeData().getSingleUrlParam(SocketIOConstants.CONNECT_ROOM_NAME), null);
        joinRoom(client, client.getHandshakeData().getSingleUrlParam(SocketIOConstants.CONNECT_APPLICATION_NAME)
                , SocketIOConstants.CONNECT_APPLICATION_NAME_ROOM_PREFIX);
        log.debug("Socket连接成功, sessionId={}, room={}", sessionId, client.getAllRooms());
    }

    /**
     * 添加OnDisconnect事件，客户端断开连接时调用
     *
     * @param client 客户端
     */
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        client.disconnect();
        log.debug("客户端断开连接, sessionId={}", client.getSessionId().toString());
    }

    /**
     * 将客户端加入指定的房间。
     * 如果提供了前缀，将会把它加到房间名前面。
     * 房间名可以是一个或多个，用逗号分隔。
     *
     * @param client 客户端实例。
     * @param room   要加入的房间名，多个房间用逗号分隔。
     * @param prefix 房间名前缀，如果不需要则为null。
     */
    private void joinRoom(SocketIOClient client, String room, String prefix) {
        if (StringUtils.isNotBlank(room)) {
            Arrays.stream(room.split(","))
                    .map(String::trim)
                    .filter(StringUtils::isNotBlank)
                    .map(roomName -> StringUtils.defaultString(prefix) + roomName)
                    .forEach(client::joinRoom);
        }
    }
}
