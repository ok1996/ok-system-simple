package cn.iosd.base.param.init;

import cn.iosd.base.param.domain.BaseParam;
import cn.iosd.base.param.service.IBaseParamService;
import cn.iosd.base.param.vo.BaseParamVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author ok1996
 */
@Component
@DependsOn("paramFlyway")
public class ParamInitLifecycle {
    @Autowired(required = false)
    private List<ParamInit> inits;
    @Autowired
    private IBaseParamService baseParamService;

    @PostConstruct
    public void init() {
        if (this.inits != null) {
            this.inits.forEach(init -> {
                String key = init.getKey();
                if (StringUtils.isEmpty(key)) {
                    throw new IllegalArgumentException("初始化key值为空");
                }
                initParam(init);
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
        BaseParam baseParam = baseParamService.selectBaseParamByKey(key);
        if (baseParam == null) {
            baseParamService.insertBaseParam(initVo(init));
        } else if (init.isRestartOverride()) {
            BaseParamVo saveReqVo = initVo(init);
            saveReqVo.setId(baseParam.getId());
            baseParamService.updateBaseParam(saveReqVo);
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
