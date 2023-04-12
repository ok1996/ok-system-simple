package cn.iosd.demo.dict.service;

import cn.iosd.demo.dict.vo.PersonRemoteVo;
import cn.iosd.demo.dict.vo.PersonVo;
import cn.iosd.demo.dict.vo.SuperPersonVo;
import cn.iosd.starter.dict.annotation.Dict;
import cn.iosd.starter.dict.vo.DictItem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * @author ok1996
 */
@Service
public class DictService {

    @Dict
    public PersonVo getPerson() {
        PersonVo personVo = new PersonVo();
        personVo.setName("z");
        personVo.setSex(1);
        return personVo;
    }

    @Dict
    public List<PersonVo> getPersonList() {
        List<PersonVo> list = new ArrayList<>();
        list.add(PersonVo.builder().name("张三").sex(1).build());
        list.add(PersonVo.builder().name("张四").sex(2).build());
        return list;
    }

    @Dict
    public SuperPersonVo getSuperPerson() {
        return SuperPersonVo.builder()
                .personVo(PersonVo.builder().name("张三").sex(1).build())
                .isLeader(1)
                .build();
    }

    @Dict
    public List<SuperPersonVo> getSuperPersonList() {
        List<SuperPersonVo> list = new ArrayList<>();
        list.add(SuperPersonVo.builder()
                .personVo(PersonVo.builder().name("张三").sex(1).build())
                .isLeader(1)
                .build());
        list.add(SuperPersonVo.builder()
                .personVo(PersonVo.builder().name("张四").sex(2).build())
                .isLeader(2)
                .build());
        return list;
    }

    public List<DictItem> remoteDict(String param) {
        List<DictItem> result = new ArrayList<>();
        String label1 = "";
        String label2 = "";
        String value1 = "1";
        String value2 = "2";
        String idCard = "idCard";
        Integer itemSize = 2;
        if (idCard.equals(param)) {
            label1 = "护照";
            label2 = "驾照";
        } else {
            label1 = "普通人";
            label2 = "超人";
        }
        for (int i = 1; i <= itemSize; i++) {
            DictItem item = new DictItem();
            item.setLabel(i == 1 ? label1 : label2);
            item.setValue(i == 1 ? value1 : value2);
            result.add(item);
        }
        return result;
    }

    @Dict
    public PersonRemoteVo getPersonRemoteVo() {
        return PersonRemoteVo.builder().name("吴小").idCard(1).hideIdentity(1).build();
    }
}
