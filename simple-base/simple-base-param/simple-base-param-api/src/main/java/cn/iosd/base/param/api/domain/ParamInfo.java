package cn.iosd.base.param.api.domain;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * @author ok1996
 */
public class ParamInfo {
    @Schema(description = "主键id")
    private Long id;
    @Schema(description = "模块名列表")
    private String moduleNames;
    @Schema(description = "参数主键")
    private String paramKey;
    @Schema(description = "参数值")
    private String codeValues;
    @Schema(description = "历史参数值")
    private String historyCodeValues;
    @Schema(description = "备注")
    private String remark;
    @Schema(description = "更新时间")
    private LocalDateTime modifyTime;
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModuleNames() {
        return moduleNames;
    }

    public void setModuleNames(String moduleNames) {
        this.moduleNames = moduleNames;
    }

    public String getParamKey() {
        return paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    public String getCodeValues() {
        return codeValues;
    }

    public void setCodeValues(String codeValues) {
        this.codeValues = codeValues;
    }

    public String getHistoryCodeValues() {
        return historyCodeValues;
    }

    public void setHistoryCodeValues(String historyCodeValues) {
        this.historyCodeValues = historyCodeValues;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
