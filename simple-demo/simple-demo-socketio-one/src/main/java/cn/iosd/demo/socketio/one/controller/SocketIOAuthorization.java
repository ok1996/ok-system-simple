package cn.iosd.demo.socketio.one.controller;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.AuthorizationResult;
import com.corundumstudio.socketio.HandshakeData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;


/**
 * 权限认证
 *
 * @author ok1996
 */
@Slf4j
@Configuration
public class SocketIOAuthorization implements AuthorizationListener {

    /**
     * 连接Url：localhost:12010?room=1003&token=1003
     *
     * @param handshakeData 请求参数
     * @return AuthorizationResult
     */
    @Override
    public AuthorizationResult getAuthorizationResult(HandshakeData handshakeData) {
        String token = handshakeData.getSingleUrlParam("token");
        String room = handshakeData.getSingleUrlParam("room");
        log.info("socketio认证参数: token={}, room={}", token, room);
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(room)) {
            log.error("socketio认证失败, 参数不符合要求: token={}, room={}", token, room);
            return AuthorizationResult.FAILED_AUTHORIZATION;
        }
        return AuthorizationResult.SUCCESSFUL_AUTHORIZATION;
    }
}
