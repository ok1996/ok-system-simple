package cn.iosd.demo.encode.service;

import cn.iosd.demo.encode.vo.PersonVo;
import cn.iosd.starter.encode.desensitized.annotation.Desensitized;
import org.springframework.stereotype.Service;

/**
 * @author 1996
 */
@Service
public class TestService {

    @Desensitized
    public PersonVo getPerson() {
        return PersonVo.builder()
                .name("张三")
                .idCard("350061323543513135")
                .address("离开省和你市好多天县谢谢谢谢谢奥所多")
                .phone("15151115112")
                .remark("我是备注奥术大师大所多")
                .normalField("艾维奇若")
                .build();
    }
}
