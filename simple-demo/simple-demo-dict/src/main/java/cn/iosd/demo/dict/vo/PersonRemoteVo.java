package cn.iosd.demo.dict.vo;

import cn.iosd.starter.dict.annotation.DictField;
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
public class PersonRemoteVo {
    @Schema(description = "姓名")
    private String name;

    @Schema(description = "身份标识")
    @DictField(dictionaryParams = "idCard", dictImplBeanName = "customDictServiceImpl", relatedField = "idCardText")
    private Integer idCard;

    private String idCardText;

    @Schema(description = "隐藏身份")
    @DictField(dictionaryParams = "hideIdentity", dictImplBeanName = "customDictServiceImpl", relatedField = "hideIdentityText")
    private Integer hideIdentity;

    private String hideIdentityText;
}
