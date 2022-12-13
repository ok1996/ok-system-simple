package cn.iosd.demo.socket.one.controller;

import cn.hutool.core.util.StrUtil;
import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.HandshakeData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;


/**
 * 权限认证
 *
 * @author ok1996
 */
@Slf4j
@Configuration
public class SocketAuthorization implements AuthorizationListener {

    /**
     * 连接Url：http://localhost:12010?room=1003&token=1003
     *
     * @param data
     * @return
     */
    @Override
    public boolean isAuthorized(HandshakeData data) {
        String token = data.getSingleUrlParam("token");
        String room = data.getSingleUrlParam("room");
        log.info("socket认证参数: token={}, room={}", token, room);
        if (StrUtil.isEmpty(token) || StrUtil.isEmpty(room)) {
            log.error("socket认证失败, 参数不符合要求: token={}, room={}", token, room);
            return false;
        }
        return true;
    }
}
