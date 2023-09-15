package cn.iosd.base.param.api.init;

import cn.iosd.base.param.api.domain.ParamInfo;
import cn.iosd.base.param.api.service.IParamInfoService;
import cn.iosd.base.param.api.vo.BaseParamVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author ok1996
 */
@Component
public class ParamInitLifecycle implements SmartLifecycle {
    private static final Logger log = LoggerFactory.getLogger(ParamInitLifecycle.class);

    @Autowired(required = false)
    private List<ParamInit> inits;

    @Autowired
    private IParamInfoService paramInfoService;

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
        RUNNING = false;
    }

    @Override
    public boolean isRunning() {
        return RUNNING;
    }

    public void init() {
        if (this.inits != null) {
            this.inits.stream()
                    .filter(Objects::nonNull)
                    .forEach(v -> {
                        if (StringUtils.isEmpty(v.getKey())) {
                            log.error("初始化key值为空,请检查ParamInit对象:{}", v);
                            return;
                        }
                        initParam(v);
                    });
        }
    }

    /**
     * 初始化参数方法
     *
     * @param init 需要初始化的参数
     */
    private void initParam(ParamInit init) {
        String key = init.getKey();
        ParamInfo paramInfo = paramInfoService.selectBaseParamByKey(key);
        if (paramInfo == null) {
            paramInfoService.insertBaseParam(initVo(init));
        } else if (init.isRestartOverride()) {
            BaseParamVo saveReqVo = initVo(init);
            saveReqVo.setId(paramInfo.getId());
            paramInfoService.updateBaseParam(saveReqVo);
        }
    }

    /**
     * 将参数转换为BaseParamSaveReqVo对象的方法
     *
     * @param init 需要转换的参数
     * @return 转换后的BaseParamSaveReqVo对象
     */
    public BaseParamVo initVo(ParamInit init) {
        BaseParamVo vo = new BaseParamVo();
        vo.setCodeValues(init.getCodeValues());
        vo.setModuleNames(init.getModuleNames());
        vo.setParamKey(init.getKey());
        vo.setRemark(init.getRemark());
        return vo;
    }
}
