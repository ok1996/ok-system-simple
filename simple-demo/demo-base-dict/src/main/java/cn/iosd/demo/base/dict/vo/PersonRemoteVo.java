package cn.iosd.demo.base.dict.vo;

import cn.iosd.starter.dict.annotation.DictField;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author ok1996
 */
public class PersonRemoteVo {
    @Schema(description = "姓名")
    private String name;

    @Schema(description = "性别")
    @DictField(dictionaryParams = "sex", relatedField = "sexText")
    private Integer sex;

    private String sexText;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getSexText() {
        return sexText;
    }

    public void setSexText(String sexText) {
        this.sexText = sexText;
    }
}
