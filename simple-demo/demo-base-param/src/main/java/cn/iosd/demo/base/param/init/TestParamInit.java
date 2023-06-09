package cn.iosd.demo.base.param.init;

import cn.iosd.base.param.init.ParamInit;
import cn.iosd.base.param.utils.ParamInitUtil;
import cn.iosd.base.param.vo.BaseParamCodeValueVo;
import cn.iosd.demo.base.param.vo.ClassmateVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 初始化key-test数据
 * <p>
 * 实现了ParamInit接口，提供一些常量和方法实现
 *
 * @author ok1996
 */
@Component
public class TestParamInit implements ParamInit {
    /**
     * 参数key
     */
    public static final String KEY = "key-test";

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public List<BaseParamCodeValueVo<?>> getCodeValues() {
        return List.of(
                new BaseParamCodeValueVo<Boolean>().setCode(ParamInitUtil.OPEN_SIMULATION_CODE).setValue(true),
                new BaseParamCodeValueVo<ClassmateVo>().setCode(ParamInitUtil.CONTENT_DATA_CODE)
                        .setValue(ClassmateVo.builder().personList(List.of(
                                        ClassmateVo.Person.builder().age(12).name("小库").build(),
                                        ClassmateVo.Person.builder().age(14).name("小明").build()))
                                .build())
        );
    }

    @Override
    public boolean restartOverride() {
        return false;
    }

    @Override
    public String getRemark() {
        return "同班同学列表";
    }

    @Override
    public List<String> getModuleNames() {
        return List.of("模块分类1", "分类1-1");
    }
}
