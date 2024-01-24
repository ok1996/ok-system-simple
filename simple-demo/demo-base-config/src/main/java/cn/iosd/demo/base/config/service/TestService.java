package cn.iosd.demo.base.config.service;

import cn.iosd.base.config.api.service.IBaseConfigService;
import cn.iosd.base.config.api.utils.ConfigUtils;
import cn.iosd.base.config.api.vo.CodeValue;
import cn.iosd.demo.base.config.init.DemoTestConfigInit;
import cn.iosd.demo.base.config.vo.ClassmateVo;
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
    private IBaseConfigService configService;

    /**
     * 获取同学列表信息
     *
     * @return 返回同学列表信息的ClassmateVo对象
     */
    public ClassmateVo classmateList() {
        if (configService == null) {
            return null;
        }
        // 从基础参数服务中获取基础参数码值对象的列表
        List<CodeValue<?>> simulation = configService.selectListByKey(DemoTestConfigInit.STUDENT_KEY_ONE);
        // 判断是否开启模拟数据
        if (!ConfigUtils.findFirstByCode(simulation, ConfigUtils.OPEN_SIMULATION_CODE, false)) {
            log.info("获取其他来源途径的数据");
            return null;
        }
        // 获取存储的模拟数据，并将其转换为对应的实体类
        return ConfigUtils.findFirstByCode(simulation, ConfigUtils.CONTENT_DATA_CODE, ClassmateVo.class,null);
    }

}
