package cn.iosd.demo.base.dict.service;

import cn.iosd.demo.base.dict.vo.PersonRemoteVo;
import cn.iosd.starter.dict.annotation.Dict;
import org.springframework.stereotype.Service;

/**
 * @author ok1996
 */
@Service
public class DictCustomTestService {

    @Dict
    public PersonRemoteVo getPersonRemoteVo() {
        PersonRemoteVo remoteVo = new PersonRemoteVo();
        remoteVo.setName("测试");
        remoteVo.setSex(1);
        return remoteVo;
    }
}
