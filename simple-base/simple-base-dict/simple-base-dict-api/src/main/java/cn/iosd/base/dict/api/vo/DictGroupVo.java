package cn.iosd.base.dict.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author ok1996
 */
public class DictGroupVo {
    @Schema(description = "字典分组参数")
    private String dictGroupParam;

    @Schema(description = "字典分组说明")
    private String dictGroupDesc;

    public String getDictGroupParam() {
        return dictGroupParam;
    }

    public void setDictGroupParam(String dictGroupParam) {
        this.dictGroupParam = dictGroupParam;
    }

    public String getDictGroupDesc() {
        return dictGroupDesc;
    }

    public void setDictGroupDesc(String dictGroupDesc) {
        this.dictGroupDesc = dictGroupDesc;
    }
}
