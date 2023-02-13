package cn.iosd.base.param.init;

import cn.iosd.base.param.domain.BaseParam;
import cn.iosd.base.param.service.IBaseParamService;
import cn.iosd.base.param.vo.BaseParamSaveReqVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ok1996
 */
@Component
public class ParamInitLifecycle implements SmartLifecycle {
    private static final Logger log = LoggerFactory.getLogger(ParamInitLifecycle.class);
    @Autowired(required = false)
    private List<ParamInit> paramInits;
    @Autowired
    private IBaseParamService baseParamService;

    private static boolean RUNNING = false;

    @Override
    public void start() {
        if (!this.isRunning()) {
            RUNNING = true;
            this.init();
        }
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return RUNNING;
    }

    public void init() {
        if (this.paramInits != null) {
            paramInits.forEach(v -> {
                String key = v.getKey();
                if (key == null || "".equals(key)) {
                    throw new IllegalArgumentException("初始化key值为空");
                }
                try {
                    BaseParam baseParam = baseParamService.selectBaseParamByKey(key);
                    if (baseParam == null) {
                        baseParamService.insertBaseParam(initVo(v));
                    } else if (v.restartOverride()) {
                        BaseParamSaveReqVo saveReqVo = initVo(v);
                        saveReqVo.setId(baseParam.getId());
                        baseParamService.updateBaseParam(saveReqVo);
                    }
                } catch (Exception e) {
                    log.error("参数[{}]初始化失败:{}", key, e.getMessage());
                }
            });
        }
    }

    public BaseParamSaveReqVo initVo(ParamInit service) {
        BaseParamSaveReqVo vo = new BaseParamSaveReqVo();
        vo.setCodeValues(service.getCodeValues());
        vo.setModuleNames(service.getModuleNames());
        vo.setParamKey(service.getKey());
        vo.setRemark(service.getRemark());
        return vo;
    }
}
