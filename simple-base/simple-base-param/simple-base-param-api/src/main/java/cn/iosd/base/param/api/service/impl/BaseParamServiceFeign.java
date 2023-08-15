package cn.iosd.base.param.api.service.impl;

import cn.iosd.base.param.api.domain.BaseParam;
import cn.iosd.base.param.api.feign.BaseParamFeign;
import cn.iosd.base.param.api.service.IBaseParamService;
import cn.iosd.base.param.api.vo.BaseParamVo;
import cn.iosd.base.param.api.vo.CodeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ok1996
 */
@Service
public class BaseParamServiceFeign implements IBaseParamService {
    @Autowired
    private BaseParamFeign baseParamFeign;

    @Override
    public BaseParam selectBaseParamByKey(String paramKey) {
        return baseParamFeign.selectBaseParamByKey(paramKey).getData();
    }

    @Override
    public List<CodeValue<?>> selectCodeValueVoParamByKey(String paramKey) {
        return baseParamFeign.selectCodeValueVoParamByKey(paramKey).getData();
    }

    @Override
    public int insertBaseParam(BaseParamVo baseParamVo) {
        return baseParamFeign.insertBaseParam(baseParamVo).getData();
    }

    @Override
    public int updateBaseParam(BaseParamVo baseParamVo) {
        return baseParamFeign.updateBaseParam(baseParamVo).getData();
    }
}
