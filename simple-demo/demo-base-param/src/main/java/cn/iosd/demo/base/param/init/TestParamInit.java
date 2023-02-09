package cn.iosd.demo.base.param.init;

import cn.hutool.core.collection.CollUtil;
import cn.iosd.base.param.init.ParamInit;
import cn.iosd.base.param.vo.BaseParamCodeValueVo;
import cn.iosd.demo.base.param.vo.ClassmateVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 初始化key-test数据
 *
 * @author ok1996
 */
@Component
public class TestParamInit implements ParamInit {
    public static final String KEY = "key-test";

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public List<BaseParamCodeValueVo<?>> getCodeValues() {

        return CollUtil.newArrayList(
                new BaseParamCodeValueVo<>().setCode("openSimulation").setValue(true),
                new BaseParamCodeValueVo<>().setCode("contentData").setValue(
                        ClassmateVo.builder().personList(
                                CollUtil.newArrayList(
                                        ClassmateVo.Person.builder().age(12).name("小库").build(),
                                        ClassmateVo.Person.builder().age(14).name("小明").build()
                                )
                        ).build()
                )
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
        return CollUtil.newArrayList("模块分类1", "分类1-1");
    }

    @Override
    public int getEnableFlag() {
        return 0;
    }
}
