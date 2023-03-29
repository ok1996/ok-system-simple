package cn.iosd.demo.dict.service;

import cn.iosd.demo.dict.vo.PersonVo;
import cn.iosd.demo.dict.vo.SuperPersonVo;
import cn.iosd.starter.dict.annotation.Dict;
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
        PersonVo personVo= new PersonVo();
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
                .isALeader(1)
                .build();
    }

    @Dict
    public List<SuperPersonVo> getSuperPersonList() {
        List<SuperPersonVo> list = new ArrayList<>();
        list.add(SuperPersonVo.builder()
                .personVo(PersonVo.builder().name("张三").sex(1).build())
                .isALeader(1)
                .build());
        list.add(SuperPersonVo.builder()
                .personVo(PersonVo.builder().name("张四").sex(2).build())
                .isALeader(2)
                .build());
        return list;
    }
}
