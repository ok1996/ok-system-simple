package cn.iosd.base.param.init;

import cn.iosd.base.param.vo.BaseParamCodeValueVo;

import java.util.List;

/**
 * 实现此方法则工程启动进行初始化数据
 *
 * @author ok1996
 */
public interface ParamInit {
    /**
     * 初始化key
     *
     * @return
     */
    String getKey();

    /**
     * 初始化配置值
     *
     * @return
     */
    List<BaseParamCodeValueVo<?>> getCodeValues();

    /**
     * 是否每次启动覆盖生成
     *
     * @return
     */
    boolean restartOverride();

    /**
     * 备注
     *
     * @return
     */
    default String getRemark() {
        return "";
    }

    /**
     * 模块列表
     *
     * @return
     */
    default List<String> getModuleNames() {
        return null;
    }

    /**
     * 是否启用 0启用 1禁用
     *
     * @return
     */
    int getEnableFlag();
}
