package cn.iosd.demo.redisson.vo;

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

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "唯一标识")
    private Integer id;
}