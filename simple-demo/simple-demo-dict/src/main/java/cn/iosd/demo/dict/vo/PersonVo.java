package cn.iosd.demo.dict.vo;

import cn.iosd.starter.dict.annotation.DictField;
import cn.iosd.starter.dict.service.impl.LocalDictServiceImpl;
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
    private String name;

    @Schema(description = "性别-默认实现类的json文件调用字典")
    @DictField(dictionaryParams = "sex", dictImplClass = LocalDictServiceImpl.class, relatedField = "sexText")
    private Integer sex;

    private String sexText;
}
