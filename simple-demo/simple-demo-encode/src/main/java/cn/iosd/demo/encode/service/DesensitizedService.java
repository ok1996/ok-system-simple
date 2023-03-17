package cn.iosd.demo.encode.service;

import cn.iosd.demo.encode.vo.PersonVo;
import cn.iosd.demo.encode.vo.SuperPersonVo;
import cn.iosd.starter.encode.desensitized.annotation.Desensitized;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 1996
 */
@Service
public class DesensitizedService {

    @Desensitized
    public PersonVo getPerson() {
        return PersonVo.builder().name("张三").idCard("350061323543513135").address("离开省和你市好多天县谢谢谢谢谢奥所多").phone("15151115112").remark("我是备注奥术大师大所多").normalField("艾维奇重新阿萨德若").build();
    }

    @Desensitized
    public List<PersonVo> getPersonList() {
        List<PersonVo> list = new ArrayList<>();
        list.add(PersonVo.builder().name("张三").idCard("350061323543513135").address("离开省和你市好多天县谢谢谢谢谢奥所多").phone("15151115112").remark("我是备注奥术大师大所多").normalField("艾维奇重新阿萨德若").build());
        list.add(PersonVo.builder().name("张三").idCard("350061323543513135").address("离开省和你市好多天县谢谢谢谢谢奥所多").phone("15151115112").remark("我是备注奥术大师大所多").normalField("艾维奇重新阿萨德若").build());
        return list;
    }


    @Desensitized
    public SuperPersonVo getSuperPerson() {
        return SuperPersonVo.builder()
                .personVo(PersonVo.builder().name("张三").idCard("350061323543513135").address("离开省和你市好多天县谢谢谢谢谢奥所多").phone("15151115112").remark("我是备注奥术大师大所多").normalField("艾维奇重新阿萨德若").build())
                .nickname("超人")
                .specificFunction("镭射眼")
                .build();
    }

    @Desensitized
    public List<SuperPersonVo> getSuperPersonList() {
        List<SuperPersonVo> list = new ArrayList<>();
        list.add(SuperPersonVo.builder()
                .personVo(PersonVo.builder().name("张三").idCard("350061323543513135").address("离开省和你市好多天县谢谢谢谢谢奥所多").phone("15151115112").remark("我是备注奥术大师大所多").normalField("艾维奇重新阿萨德若").build())
                .nickname("超人")
                .specificFunction("镭射眼")
                .build());
        list.add(SuperPersonVo.builder()
                .personVo(PersonVo.builder().name("张三").idCard("350061323543513135").address("离开省和你市好多天县谢谢谢谢谢奥所多").phone("15151115112").remark("我是备注奥术大师大所多").normalField("艾维奇重新阿萨德若").build())
                .nickname("超人")
                .specificFunction("镭射眼")
                .build());
        return list;
    }

}
