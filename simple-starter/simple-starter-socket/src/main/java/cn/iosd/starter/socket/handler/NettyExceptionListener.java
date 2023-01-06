package cn.iosd.starter.socket.handler;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ExceptionListener;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * netty异常处理器
 *
 * @author ok1996
 */
@Component
public class NettyExceptionListener implements ExceptionListener {
    private static final Logger log = LoggerFactory.getLogger(NettyExceptionListener.class);

    @Override
    public void onEventException(Exception e, List<Object> args, SocketIOClient client) {
        log.error("socket事件异常, {}", e.getMessage(), e);
    }

    @Override
    public void onDisconnectException(Exception e, SocketIOClient client) {
        log.error("socket断开事件异常, {}", e.getMessage(), e);
    }

    @Override
    public void onConnectException(Exception e, SocketIOClient client) {
        log.error("socket连接异常, {}", e.getMessage(), e);
    }

    @Override
    public boolean exceptionCaught(ChannelHandlerContext ctx, Throwable e) {
        log.error("socket连接异常, {}", e.getMessage(), e);
        return false;
    }

    @Override
    public void onPingException(Exception e, SocketIOClient client) {
        log.error("socket心跳异常Ping, {}", e.getMessage(), e);
    }

    @Override
    public void onPongException(Exception e, SocketIOClient client) {
        log.error("socket心跳异常Pong, {}", e.getMessage(), e);
    }
}
