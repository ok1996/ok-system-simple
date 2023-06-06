package cn.iosd.demo.base.param.service;

import cn.iosd.base.param.service.IBaseParamService;
import cn.iosd.base.param.utils.ParamInitUtil;
import cn.iosd.base.param.vo.BaseParamCodeValueVo;
import cn.iosd.demo.base.param.init.TestParamInit;
import cn.iosd.demo.base.param.vo.ClassmateVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author ok1996
 */
@Slf4j
@Service
public class TestService {
    @Autowired(required = false)
    private IBaseParamService baseParamService;

    /**
     * 获取同学列表信息
     *
     * @return 返回同学列表信息的ClassmateVo对象
     */
    public ClassmateVo classmateList() {
        if (baseParamService == null) {
            return null;
        }
        // 从基础参数服务中获取基础参数码值对象的列表
        List<BaseParamCodeValueVo<?>> simulation = baseParamService.selectCodeValueVoParamByKey(TestParamInit.KEY);
        // 判断是否开启模拟数据
        if (!ParamInitUtil.getBooleanValueByCodeDefaultFalse(simulation, ParamInitUtil.OPEN_SIMULATION_CODE)) {
            // TODO: 实现获取其他来源途径的数据的代码
            log.info("获取其他来源途径的数据");
            return null;
        }
        // 获取存储的模拟数据，并将其转换为对应的实体类
        Optional<BaseParamCodeValueVo<?>> contentData = ParamInitUtil.getBaseParamCodeValueVoByCode(simulation, ParamInitUtil.CONTENT_DATA_CODE);
        if (contentData.isPresent()) {
            return ParamInitUtil.readValue(contentData.get(), ClassmateVo.class);
        }
        log.error("模拟数据不存在,key:{}", TestParamInit.KEY);
        return null;
    }

}
