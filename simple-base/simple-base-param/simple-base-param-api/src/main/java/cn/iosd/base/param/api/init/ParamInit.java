package cn.iosd.base.param.api.init;

import cn.iosd.base.param.api.vo.CodeValue;

import java.util.List;

/**
 * 实现此对象则工程启动进行初始化数据
 *
 * @author ok1996
 */
public class ParamInit {
    /**
     * 初始化参数的键。
     */
    private String key;

    /**
     * 获取初始化参数的配置值列表。
     */
    private List<CodeValue<?>> codeValues;

    /**
     * 是否每次启动都覆盖生成。
     * <br/>
     * true表示每次启动覆盖生成；false表示不覆盖
     */
    private boolean restartOverride = false;

    /**
     * 备注信息。
     */
    private String remark = "";

    /**
     * 模块名称列表。
     */
    private List<String> moduleNames;

    public ParamInit() {
    }

    /**
     * 初始化对象
     *
     * @param key             初始化参数的键
     * @param remark          备注信息
     * @param restartOverride 是否每次启动都覆盖生成
     * @param moduleNames     模块名称列表
     */
    public ParamInit(String key, String remark, boolean restartOverride, List<String> moduleNames) {
        this.key = key;
        this.moduleNames = moduleNames;
        this.remark = remark;
        this.restartOverride = restartOverride;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<CodeValue<?>> getCodeValues() {
        return codeValues;
    }

    public void setCodeValues(List<CodeValue<?>> codeValues) {
        this.codeValues = codeValues;
    }

    public boolean isRestartOverride() {
        return restartOverride;
    }

    public void setRestartOverride(boolean restartOverride) {
        this.restartOverride = restartOverride;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<String> getModuleNames() {
        return moduleNames;
    }

    public void setModuleNames(List<String> moduleNames) {
        this.moduleNames = moduleNames;
    }

    @Override
    public String toString() {
        return "ParamInit{" +
                "key='" + key + '\'' +
                ", codeValues=" + codeValues +
                ", restartOverride=" + restartOverride +
                ", remark='" + remark + '\'' +
                ", moduleNames=" + moduleNames +
                '}';
    }
}
