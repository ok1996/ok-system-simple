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
import java.util.HashSet;

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
        subscribeToRedis();
        log.info("SocketIOServer启动");
    }

    private void subscribeToRedis() {
        pubSubStore.subscribe(PubSubType.DISPATCH, this::handleDispatchMessage, DispatchMessage.class);
    }

    private void handleDispatchMessage(DispatchMessage msg) {
        String room = msg.getRoom();
        Packet packet = msg.getPacket();
        Object socketMessage = packet.getData();
        log.info("收到订阅消息：DispatchMessage={}", socketMessage);

        Collection<SocketIOClient> clients = getClients(room);
        clients.forEach(client -> client.sendEvent(packet.getName(), socketMessage));
    }

    private Collection<SocketIOClient> getClients(String room) {
        // 判断是否发送给所有客户端
        if (StringUtils.isEmpty(room) || SocketConstants.SEND_ALL.equals(room)) {
            return socketIoServer.getBroadcastOperations().getClients();
        }
        return new HashSet<>(socketIoServer.getRoomOperations(room).getClients());
    }

}