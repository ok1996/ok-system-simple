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
     * 获取初始化参数的键。
     *
     * @return 参数键
     */
    String getKey();

    /**
     * 获取初始化参数的配置值列表。
     *
     * @return 配置值列表
     */
    List<BaseParamCodeValueVo<?>> getCodeValues();

    /**
     * 判断是否每次启动都覆盖生成。
     *
     * @return true表示每次启动覆盖生成；false表示不覆盖
     */
    boolean restartOverride();

    /**
     * 获取备注信息。
     *
     * @return 备注信息
     */
    default String getRemark() {
        return "";
    }

    /****
     * 获取模块名称列表。
     *
     * @return 模块名称列表
     */
    default List<String> getModuleNames() {
        return null;
    }
}
