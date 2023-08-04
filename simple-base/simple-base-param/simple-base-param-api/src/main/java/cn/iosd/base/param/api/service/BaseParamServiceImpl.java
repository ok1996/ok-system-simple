package cn.iosd.base.param.api.service;

import cn.iosd.base.param.api.domain.BaseParam;
import cn.iosd.base.param.api.feign.BaseParamServiceFeign;
import cn.iosd.base.param.api.vo.BaseParamVo;
import cn.iosd.base.param.api.vo.CodeValue;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ok1996
 */
@Service
public class BaseParamServiceImpl implements IBaseParamService {
    @Autowired
    private BaseParamServiceFeign baseParamServiceFeign;

    @Override
    public BaseParam selectBaseParamByKey(String paramKey) {
        return baseParamServiceFeign.selectBaseParamByKey(paramKey).getData();
    }

    @Override
    public List<CodeValue<?>> selectCodeValueVoParamByKey(String paramKey) {
        return baseParamServiceFeign.selectCodeValueVoParamByKey(paramKey).getData();
    }

    @Override
    public int insertBaseParam(BaseParamVo baseParamVo) {
        return baseParamServiceFeign.insertBaseParam(baseParamVo).getData();
    }

    @Override
    public int updateBaseParam(BaseParamVo baseParamVo) {
        return baseParamServiceFeign.updateBaseParam(baseParamVo).getData();
    }
}
