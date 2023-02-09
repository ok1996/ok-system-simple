package cn.iosd.demo.base.param.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ClassmateVo {
    @Schema(description = "人员列表")
    private List<Person> personList;

    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Person {
        @Schema(description = "姓名")
        private String name;
        @Schema(description = "年龄")
        private Integer age;
    }
}
