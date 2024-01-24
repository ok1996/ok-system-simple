package cn.iosd.base.config.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * @author ok1996
 */
public class CodeValueListHistory {
    @Schema(description = "时间戳")
    private Long time;

    @Schema(description = "历史配置参数")
    private List<CodeValue<?>> codeValueList;

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public List<CodeValue<?>> getCodeValueList() {
        return codeValueList;
    }

    public void setCodeValueList(List<CodeValue<?>> codeValueList) {
        this.codeValueList = codeValueList;
    }
}
