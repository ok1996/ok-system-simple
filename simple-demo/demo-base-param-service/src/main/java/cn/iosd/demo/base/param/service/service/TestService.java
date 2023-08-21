package cn.iosd.demo.base.param.service.service;

import cn.iosd.base.param.api.service.IBaseParamService;
import cn.iosd.base.param.api.utils.ParamInitUtil;
import cn.iosd.base.param.api.vo.CodeValue;
import cn.iosd.demo.base.param.service.init.DemoTestParamInit;
import cn.iosd.demo.base.param.service.vo.ClassmateVo;
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
        List<CodeValue<?>> simulation = baseParamService.selectCodeValueVoParamByKey(DemoTestParamInit.STUDENT_KEY_ONE);
        // 判断是否开启模拟数据
        if (!ParamInitUtil.getBooleanValueByCodeDefaultFalse(simulation, ParamInitUtil.OPEN_SIMULATION_CODE)) {
            log.info("获取其他来源途径的数据");
            return null;
        }
        // 获取存储的模拟数据，并将其转换为对应的实体类
        Optional<CodeValue<?>> contentData = ParamInitUtil.getCodeValueByCode(simulation, ParamInitUtil.CONTENT_DATA_CODE);
        if (contentData.isPresent()) {
            return ParamInitUtil.readValue(contentData.get(), ClassmateVo.class);
        }
        log.error("模拟数据不存在,key:{}", DemoTestParamInit.STUDENT_KEY_ONE);
        return null;
    }

}