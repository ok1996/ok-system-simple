package cn.iosd.starter.socket.service;

import cn.iosd.starter.socket.constant.SocketConstants;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.protocol.Packet;
import com.corundumstudio.socketio.protocol.PacketType;
import com.corundumstudio.socketio.store.pubsub.DispatchMessage;
import com.corundumstudio.socketio.store.pubsub.PubSubStore;
import com.corundumstudio.socketio.store.pubsub.PubSubType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author ok1996
 */
@Service
public class SocketMessageServer {
    private static final Logger log = LoggerFactory.getLogger(SocketMessageServer.class);

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    private SocketIOServer socketIoServer;

    @Autowired
    private PubSubStore pubSubStore;

    /**
     * 广播消息事件
     *
     * @param event 事件名称
     * @param msg   消息体，可以是任何Object类型的对象
     */
    public void sendBroadcast(String event, Object msg) {
        Collection<SocketIOClient> clients = socketIoServer.getBroadcastOperations().getClients();
        clients.forEach(client -> client.sendEvent(event, msg));
        log.info("向所有客户端推送广播消息, event={}", event);

        publishMessage(SocketConstants.SEND_ALL, event, msg);
    }

    /**
     * 向指定房间发送消息
     *
     * @param event 消息事件名称
     * @param room  房间名称
     * @param msg   消息体，可以是任何Object类型的对象
     */
    public void sendRoom(String event, String room, Object msg) {
        Collection<SocketIOClient> clients = socketIoServer.getRoomOperations(room).getClients();
        clients.forEach(client -> client.sendEvent(event, msg));
        log.info("向房间{}中的客户端推送消息, event={}", room, event);

        publishMessage(room, event, msg);
    }

    /**
     * 自动获取本微服务名称并向当前微服务下的所有连接发送消息
     * <p>注：会往连接applicationName参数为空的客户端发送msg
     *
     * @param event 事件名称
     * @param msg   消息体
     */
    public void sendService(String event, Object msg) {
        Collection<SocketIOClient> clients = new HashSet<>(socketIoServer.getRoomOperations(SocketConstants.CONNECT_APPLICATION_NAME_ROOM_PREFIX
                + applicationName).getClients());
        clients.forEach(client -> client.sendEvent(event, msg));
        log.info("向连接参数为applicationName={}的客户端发送消息, event={}", applicationName, event);

        publishMessage(SocketConstants.CONNECT_APPLICATION_NAME_ROOM_PREFIX + applicationName, event, msg);
    }

    private void publishMessage(String room, String event, Object msg) {
        Packet packet = new Packet(PacketType.MESSAGE);
        packet.setSubType(PacketType.EVENT);
        packet.setName(event);
        packet.setData(msg);
        packet.setNsp("");
        pubSubStore.publish(PubSubType.DISPATCH, new DispatchMessage(room, packet, ""));
    }
}
