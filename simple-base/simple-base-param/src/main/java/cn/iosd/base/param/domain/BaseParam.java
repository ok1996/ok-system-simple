package cn.iosd.base.param.domain;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * @author ok1996
 */
public class BaseParam {
    @Schema(description = "主键id")
    private Long id;
    @Schema(description = "模块名列表")
    private JsonNode moduleNames;
    @Schema(description = "参数主键")
    private String paramKey;
    @Schema(description = "参数值")
    private JsonNode codeValues;
    @Schema(description = "历史参数值")
    private JsonNode historyCodeValues;
    @Schema(description = "备注")
    private String remark;
    @Schema(description = "是否启用 0启用 1禁用")
    private Integer enableFlag;
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

    public JsonNode getModuleNames() {
        return moduleNames;
    }

    public void setModuleNames(JsonNode moduleNames) {
        this.moduleNames = moduleNames;
    }

    public String getParamKey() {
        return paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    public JsonNode getCodeValues() {
        return codeValues;
    }

    public void setCodeValues(JsonNode codeValues) {
        this.codeValues = codeValues;
    }

    public JsonNode getHistoryCodeValues() {
        return historyCodeValues;
    }

    public void setHistoryCodeValues(JsonNode historyCodeValues) {
        this.historyCodeValues = historyCodeValues;
    }

    public Integer getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(Integer enableFlag) {
        this.enableFlag = enableFlag;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
