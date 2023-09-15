package cn.iosd.base.param.api.service.impl;

import cn.iosd.base.param.api.domain.ParamInfo;
import cn.iosd.base.param.api.feign.ParamInfoFeign;
import cn.iosd.base.param.api.service.IParamInfoService;
import cn.iosd.base.param.api.vo.BaseParamVo;
import cn.iosd.base.param.api.vo.CodeValue;
import cn.iosd.base.param.api.vo.CodeValueListHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ok1996
 */
@Service
public class ParamInfoServiceFeignImpl implements IParamInfoService {
    @Autowired
    private ParamInfoFeign paramInfoFeign;

    @Override
    public ParamInfo selectBaseParamByKey(String paramKey) {
        return paramInfoFeign.selectBaseParamByKey(paramKey).dataOrThrowExceptionIfNotSuccess();
    }

    @Override
    public List<CodeValue<?>> selectCodeValueVoParamByKey(String paramKey) {
        return paramInfoFeign.selectCodeValueVoParamByKey(paramKey).dataOrThrowExceptionIfNotSuccess();
    }

    @Override
    public int insertBaseParam(BaseParamVo baseParamVo) {
        return paramInfoFeign.insertBaseParam(baseParamVo).dataOrThrowExceptionIfNotSuccess();
    }

    @Override
    public int updateBaseParam(BaseParamVo baseParamVo) {
        return paramInfoFeign.updateBaseParam(baseParamVo).dataOrThrowExceptionIfNotSuccess();
    }

    @Override
    public List<CodeValueListHistory> selectCodeValueHistoryParamByKey(String paramKey) {
        return paramInfoFeign.selectCodeValueHistoryParamByKey(paramKey).dataOrThrowExceptionIfNotSuccess();
    }
}
