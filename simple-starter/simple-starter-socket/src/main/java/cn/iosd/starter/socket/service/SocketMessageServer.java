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
import java.util.stream.Collectors;

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
        for (final SocketIOClient client : clients) {
            client.sendEvent(event, msg);
            log.info("向客户端推送广播消息, event={}", event);
        }
        Packet packet = new Packet(PacketType.MESSAGE);
        packet.setSubType(PacketType.EVENT);
        packet.setName(event);
        packet.setData(msg);
        packet.setNsp("");
        pubSubStore.publish(PubSubType.DISPATCH, new DispatchMessage(SocketConstants.SEND_ALL, packet, ""));
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
        for (SocketIOClient client : clients) {
            client.sendEvent(event, msg);
            log.info("向客户端推送消息Room:{}, event={}", room, event);
        }
        Packet packet = new Packet(PacketType.MESSAGE);
        packet.setSubType(PacketType.EVENT);
        packet.setName(event);
        packet.setData(msg);
        packet.setNsp("");
        pubSubStore.publish(PubSubType.DISPATCH, new DispatchMessage(room, packet, ""));
    }

    /**
     * 自动获取本微服务名称并向当前微服务下的所有连接发送消息
     * <p>注：会往连接applicationName参数为空的客户端发送msg
     *
     * @param event 事件名称
     * @param msg   消息体
     */
    public void sendService(String event, Object msg) {
        Collection<SocketIOClient> clients = socketIoServer.getRoomOperations(SocketConstants.CONNECT_APPLICATION_NAME_ROOM_PREFIX
                + applicationName).getClients();
        clients.addAll(getEmptyConnectRoom());
        for (SocketIOClient client : clients) {
            client.sendEvent(event, msg);
            log.info("向客户端推送消息微服务:{}, event={}", applicationName, event);
        }
        Packet packet = new Packet(PacketType.MESSAGE);
        packet.setSubType(PacketType.EVENT);
        packet.setName(event);
        packet.setData(msg);
        packet.setNsp("");
        pubSubStore.publish(PubSubType.DISPATCH, new DispatchMessage(SocketConstants.CONNECT_APPLICATION_NAME_ROOM_PREFIX
                + applicationName, packet, ""));
    }

    public Collection<SocketIOClient> getEmptyConnectRoom() {
        Collection<SocketIOClient> clientsAll = socketIoServer.getBroadcastOperations().getClients();
        return clientsAll.stream().filter(v -> v.getAllRooms().size() == 1
                && v.getAllRooms().contains("")
        ).collect(Collectors.toList());
    }
}
