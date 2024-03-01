package cn.iosd.demo.dict.vo;

import cn.iosd.starter.dict.annotation.DictEntity;
import cn.iosd.starter.dict.annotation.DictField;
import cn.iosd.demo.dict.service.dict.LocalDictServiceImpl;
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
public class SuperPersonVo {

    @DictEntity
    private PersonVo personVo;

    @Schema(description = "是否是领导-默认实现类的json文件调用字典")
    @DictField(dictionaryParams = "leader", dictImplClass = LocalDictServiceImpl.class, relatedField = "isLeaderText")
    private Integer isLeader;

    private String isLeaderText;
}
