package cn.iosd.starter.socket.runner;

import cn.iosd.starter.socket.constant.SocketConstants;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.protocol.Packet;
import com.corundumstudio.socketio.store.pubsub.DispatchMessage;
import com.corundumstudio.socketio.store.pubsub.PubSubStore;
import com.corundumstudio.socketio.store.pubsub.PubSubType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SocketIOServer启动器
 *
 * @author ok1996
 */
@Component
public class SocketServerRunner implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(SocketServerRunner.class);

    @Autowired
    private SocketIOServer socketIoServer;

    @Autowired
    private PubSubStore pubSubStore;

    @Override
    public void run(String... args) {
        socketIoServer.start();
        //订阅redis队列
        pubSubStore.subscribe(PubSubType.DISPATCH, msg -> {
            String room = msg.getRoom();
            Packet packet = msg.getPacket();
            Object socketMessage = packet.getData();
            log.info("收到订阅消息：DispatchMessage={}", socketMessage);
            Collection<SocketIOClient> clients;
            if (StringUtils.isEmpty(room) || SocketConstants.SEND_ALL.equals(room)) {
                clients = socketIoServer.getBroadcastOperations().getClients();
            } else {
                clients = socketIoServer.getRoomOperations(room).getClients();
                if (room.contains(SocketConstants.CONNECT_APPLICATION_NAME_ROOM_PREFIX)) {
                    //加入参数为空的客户端一起推送数据
                    Collection<SocketIOClient> clientsAll = socketIoServer.getBroadcastOperations().getClients();
                    List<SocketIOClient> clientNotSpecifiedList = clientsAll.stream()
                            .filter(v -> v.getAllRooms().size() == 1
                                    && v.getAllRooms().contains("")
                            ).collect(Collectors.toList());
                    clients.addAll(clientNotSpecifiedList);
                }
            }

            for (final SocketIOClient client : clients) {
                client.sendEvent(packet.getName(), socketMessage);
            }
        }, DispatchMessage.class);
        log.info("SocketIOServer启动");
    }

}