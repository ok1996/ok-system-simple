package cn.iosd.base.dict.api.domain;

import io.swagger.v3.oas.annotations.media.Schema;


/**
 * <p>
 * 字典数据
 * </p>
 *
 * @author ok1996
 */

@Schema(name = "DictInfo", description = "字典数据")
public class DictInfo {

    @Schema(description = "主键id")
    private Long id;

    @Schema(description = "字典分组参数")
    private String dictGroupParam;

    @Schema(description = "字典分组说明")
    private String dictGroupDesc;

    @Schema(description = "字典编码")
    private String dictValue;

    @Schema(description = "字典描述")
    private String dictDesc;

    @Schema(description = "字典备注")
    private String dictRemark;

    @Schema(description = "字典排序")
    private Integer dictSort;

    @Schema(description = "字典状态（0正常 1停用）")
    private Integer dictStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getDictValue() {
        return dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
    }

    public String getDictDesc() {
        return dictDesc;
    }

    public void setDictDesc(String dictDesc) {
        this.dictDesc = dictDesc;
    }

    public String getDictRemark() {
        return dictRemark;
    }

    public void setDictRemark(String dictRemark) {
        this.dictRemark = dictRemark;
    }

    public Integer getDictSort() {
        return dictSort;
    }

    public void setDictSort(Integer dictSort) {
        this.dictSort = dictSort;
    }

    public Integer getDictStatus() {
        return dictStatus;
    }

    public void setDictStatus(Integer dictStatus) {
        this.dictStatus = dictStatus;
    }

    @Override
    public String toString() {
        return "DictInfo{" +
            "id = " + id +
            ", dictGroupParam = " + dictGroupParam +
            ", dictGroupDesc = " + dictGroupDesc +
            ", dictValue = " + dictValue +
            ", dictDesc = " + dictDesc +
            ", dictRemark = " + dictRemark +
            ", dictSort = " + dictSort +
            ", dictStatus = " + dictStatus +
        "}";
    }
}
