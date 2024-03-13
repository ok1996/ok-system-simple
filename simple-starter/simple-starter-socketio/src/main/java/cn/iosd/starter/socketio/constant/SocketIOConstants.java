package cn.iosd.starter.socketio.constant;

/**
 * Socket常量信息
 *
 * @author ok1996
 */
public class SocketIOConstants {

    /**
     * socket连接参数-房间号
     */
    public final static String CONNECT_ROOM_NAME = "room";

    /**
     * socket连接参数-应用名称
     */
    public final static String CONNECT_APPLICATION_NAME = "applicationName";

    /**
     * 微服务加入群聊Room前缀
     */
    public final static String CONNECT_APPLICATION_NAME_ROOM_PREFIX = CONNECT_APPLICATION_NAME + "-";

    /**
     * 向客户端推送广播消息
     */
    public final static String SEND_ALL = "all";
}
