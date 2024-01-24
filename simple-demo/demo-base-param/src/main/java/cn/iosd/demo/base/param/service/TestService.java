package cn.iosd.demo.base.param.service;

import cn.iosd.base.param.api.service.IParamInfoService;
import cn.iosd.base.param.api.utils.ParamInitUtil;
import cn.iosd.base.param.api.vo.CodeValue;
import cn.iosd.demo.base.param.init.DemoTestParamInit;
import cn.iosd.demo.base.param.vo.ClassmateVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ok1996
 */
@Slf4j
@Service
public class TestService {
    @Autowired(required = false)
    private IParamInfoService paramInfoService;

    /**
     * 获取同学列表信息
     *
     * @return 返回同学列表信息的ClassmateVo对象
     */
    public ClassmateVo classmateList() {
        if (paramInfoService == null) {
            return null;
        }
        // 从基础参数服务中获取基础参数码值对象的列表
        List<CodeValue<?>> simulation = paramInfoService.selectCodeValueVoParamByKey(DemoTestParamInit.STUDENT_KEY_ONE);
        // 判断是否开启模拟数据
        if (!ParamInitUtil.findFirstByCode(simulation, ParamInitUtil.OPEN_SIMULATION_CODE, false)) {
            log.info("获取其他来源途径的数据");
            return null;
        }
        // 获取存储的模拟数据，并将其转换为对应的实体类
        return ParamInitUtil.findFirstByCode(simulation, ParamInitUtil.CONTENT_DATA_CODE, ClassmateVo.class,null);
    }

}
