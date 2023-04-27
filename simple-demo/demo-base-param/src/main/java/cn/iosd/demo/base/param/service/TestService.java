package cn.iosd.demo.base.param.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.iosd.base.param.service.IBaseParamService;
import cn.iosd.base.param.utils.ParamInitUtil;
import cn.iosd.base.param.vo.BaseParamCodeValueVo;
import cn.iosd.demo.base.param.init.TestParamInit;
import cn.iosd.demo.base.param.vo.ClassmateVo;

/**
 * @author ok1996
 */
@Service
public class TestService {
    @Autowired(required = false)
    private IBaseParamService baseParamService;

    /**
     * 获取同学列表信息
     * @return 返回同学列表信息的ClassmateVo对象
     */
    public ClassmateVo classmateList() {
        if (baseParamService != null) {
            // 从基础参数服务中获取基础参数码值对象的列表
            List<BaseParamCodeValueVo<?>> simulation = baseParamService.selectCodeValueVoParamByKey(TestParamInit.KEY);
            // 判断是否开启模拟数据
            if (ParamInitUtil.getBooleanValueByCodeDefaultFalse(simulation, TestParamInit.OPEN_SIMULATION_CODE)) {
                // 获取存储的模拟数据，并将其转换为对应的实体类
                Optional<BaseParamCodeValueVo<?>> contentData = ParamInitUtil.getBaseParamCodeValueVoByCode(simulation, TestParamInit.CONTENT_DATA_CODE);
                if (contentData.isPresent()) {
                    return ParamInitUtil.readValue(contentData.get(), ClassmateVo.class);
                }
            } else {
                // TODO: 实现获取其他来源途径的数据的代码
            }
        }
        return null;
    }

}
