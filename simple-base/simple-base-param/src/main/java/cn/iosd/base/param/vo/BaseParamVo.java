package cn.iosd.base.param.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * @author ok1996
 */
public class BaseParamVo {
    @Schema(description = "主键id")
    private Long id;
    @Schema(description = "模块名列表",example = "[\"demo\",\"test\"]")
    private List<String> moduleNames;
    @Schema(description = "参数主键")
    private String paramKey;
    @Schema(description = "参数值",example = "[{\"code\":\"age\",\"value\":100},{\"code\":\"sex\",\"value\":\"女性\"}]")
    private List<CodeValue<?>> codeValues;
    @Schema(description = "备注")
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getModuleNames() {
        return moduleNames;
    }

    public void setModuleNames(List<String> moduleNames) {
        this.moduleNames = moduleNames;
    }

    public String getParamKey() {
        return paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    public List<CodeValue<?>> getCodeValues() {
        return codeValues;
    }

    public void setCodeValues(List<CodeValue<?>> codeValues) {
        this.codeValues = codeValues;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
