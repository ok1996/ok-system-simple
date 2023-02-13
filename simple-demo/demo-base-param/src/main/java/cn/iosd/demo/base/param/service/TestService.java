package cn.iosd.demo.base.param.service;

import cn.iosd.base.param.service.IBaseParamService;
import cn.iosd.base.param.utils.ParamInitUtil;
import cn.iosd.base.param.vo.BaseParamCodeValueVo;
import cn.iosd.demo.base.param.init.TestParamInit;
import cn.iosd.demo.base.param.vo.ClassmateVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author ok1996
 */
@Service
public class TestService {
    @Autowired(required = false)
    private IBaseParamService baseParamService;

    public ClassmateVo classmateList() throws JsonProcessingException {
        if (baseParamService != null) {
            List<BaseParamCodeValueVo<?>> simulation = baseParamService.selectCodeValueVoParamByKey(TestParamInit.KEY);
            if (ParamInitUtil.getBooleanValueByCodeDefaultFalse(simulation, TestParamInit.OPEN_SIMULATION_CODE)) {
                Optional<BaseParamCodeValueVo<?>> contentData = ParamInitUtil.getBaseParamCodeValueVoByCode(simulation, TestParamInit.CONTENT_DATA_CODE);
                if (contentData.isPresent()) {
                    return ParamInitUtil.readValue(contentData.get(), ClassmateVo.class);
                }
            }
        }
        return null;
    }
}
