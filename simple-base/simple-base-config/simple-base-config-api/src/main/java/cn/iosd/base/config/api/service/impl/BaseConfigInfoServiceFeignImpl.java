package cn.iosd.base.config.api.service.impl;

import cn.iosd.base.config.api.domain.BaseConfigInfo;
import cn.iosd.base.config.api.feign.BaseConfigInfoFeign;
import cn.iosd.base.config.api.service.IBaseConfigService;
import cn.iosd.base.config.api.vo.BaseConfigVo;
import cn.iosd.base.config.api.vo.CodeValue;
import cn.iosd.base.config.api.vo.CodeValueListHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ok1996
 */
@Service
public class BaseConfigInfoServiceFeignImpl implements IBaseConfigService {
    @Autowired
    private BaseConfigInfoFeign baseConfigInfoFeign;

    @Override
    public BaseConfigInfo selectByKey(String key) {
        return baseConfigInfoFeign.selectByKey(key).checkThrow().getData();
    }

    @Override
    public List<CodeValue<?>> selectValueListByKey(String key) {
        return baseConfigInfoFeign.selectValueListByKey(key).checkThrow().getData();
    }

    @Override
    public int insert(BaseConfigVo baseConfigVo) {
        return baseConfigInfoFeign.insert(baseConfigVo).checkThrow().getData();
    }

    @Override
    public int update(BaseConfigVo baseConfigVo) {
        return baseConfigInfoFeign.update(baseConfigVo).checkThrow().getData();
    }

    @Override
    public List<CodeValueListHistory> selectValueListHistoryByKey(String key) {
        return baseConfigInfoFeign.selectValueListHistoryByKey(key).checkThrow().getData();
    }
}
