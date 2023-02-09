package cn.iosd.base.param.vo;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author ok1996
 */
public class BaseParamListReqVo {
    @Schema(description = "模块名列表")
    private JsonNode moduleNames;
    @Schema(description = "参数主键")
    private String paramKey;
    @Schema(description = "备注")
    private String remark;
    @Schema(description = "是否启用 0启用 1禁用")
    private Integer enableFlag;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(Integer enableFlag) {
        this.enableFlag = enableFlag;
    }
}
