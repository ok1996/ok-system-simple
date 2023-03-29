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
public class PersonVo {
    @Schema(description = "姓名")
    private String name;

    @Schema(description = "性别")
    @DictField(path = "E:\\..\\src\\main\\resources\\sex.json", source = "localDictServiceImpl", relatedField = "sexText")
    private Integer sex;

    private String sexText;
}
