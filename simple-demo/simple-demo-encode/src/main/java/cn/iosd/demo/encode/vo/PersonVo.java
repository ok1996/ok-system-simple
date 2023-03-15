package cn.iosd.demo.encode.vo;

import cn.iosd.starter.encode.desensitized.annotation.SensitiveField;
import cn.iosd.starter.encode.desensitized.vo.SensitiveRule;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ok1996
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonVo {
    @Schema(description = "姓名")
    @SensitiveField(rule = SensitiveRule.CHINESE_NAME)
    private String name;

    @Schema(description = "手机号")
    @SensitiveField(rule = SensitiveRule.MOBILE_PHONE)
    private String phone;

    @Schema(description = "地址")
    @SensitiveField(rule = SensitiveRule.ADDRESS)
    private String address;

    @Schema(description = "备注")
    @SensitiveField(rule = SensitiveRule.CUSTOM, prefixLen = 2, suffixLen = 2)
    private String remark;

    @Schema(description = "身份证")
    @SensitiveField(rule = SensitiveRule.ID_CARD)
    private String idCard;

    @Schema(description = "普通字段")
    private String normalField;
}
