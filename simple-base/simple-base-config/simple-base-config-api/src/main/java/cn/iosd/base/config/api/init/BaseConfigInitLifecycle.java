package cn.iosd.base.config.api.init;

import cn.iosd.base.config.api.domain.BaseConfigInfo;
import cn.iosd.base.config.api.service.IBaseConfigService;
import cn.iosd.base.config.api.vo.BaseConfigVo;
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
public class BaseConfigInitLifecycle implements SmartLifecycle {
    private static final Logger log = LoggerFactory.getLogger(BaseConfigInitLifecycle.class);

    @Autowired(required = false)
    private List<ConfigInit> inits;

    @Autowired
    private IBaseConfigService service;

    private boolean running = false;

    @Override
    public void start() {
        if (!this.isRunning()) {
            running = true;
            this.init();
        }
    }

    @Override
    public void stop() {
        running = false;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    public void init() {
        if (this.inits != null) {
            this.inits.stream()
                    .filter(Objects::nonNull)
                    .filter(v -> StringUtils.isNotEmpty(v.getKey()))
                    .forEach(this::initParam);
        }
    }

    /**
     * 初始化参数方法
     *
     * @param init 需要初始化的参数
     */
    private void initParam(ConfigInit init) {
        String key = init.getKey();
        BaseConfigInfo baseConfigInfo = service.selectByKey(key);
        if (baseConfigInfo == null) {
            log.info("Initialize configuration data with key：{}", key);
            service.insert(convertToBaseParamVo(init));
        } else if (init.isRestartOverride()) {
            log.info("Override configuration data with key：{}", key);
            BaseConfigVo saveReqVo = convertToBaseParamVo(init);
            saveReqVo.setId(baseConfigInfo.getId());
            service.update(saveReqVo);
        }
    }

    /**
     * 将参数转换为BaseParamSaveReqVo对象的方法
     *
     * @param init 需要转换的参数
     * @return 转换后的BaseParamSaveReqVo对象
     */
    public BaseConfigVo convertToBaseParamVo(ConfigInit init) {
        BaseConfigVo vo = new BaseConfigVo();
        vo.setCodeValues(init.getCodeValues());
        vo.setModuleNames(init.getModuleNames());
        vo.setParamKey(init.getKey());
        vo.setRemark(init.getRemark());
        return vo;
    }
}
