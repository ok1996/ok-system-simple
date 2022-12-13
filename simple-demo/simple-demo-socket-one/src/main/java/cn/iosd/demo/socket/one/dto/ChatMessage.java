package cn.iosd.demo.socket.one.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 通信消息实体
 *
 * @author ok1996
 */
@Data
@ToString
public class ChatMessage implements Serializable {

    private String message;

    private String eventName;

    private String room;
}
