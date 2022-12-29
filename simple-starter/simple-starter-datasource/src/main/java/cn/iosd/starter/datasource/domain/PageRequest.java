package cn.iosd.starter.datasource.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author ok1996
 */
@Data
@Schema(description = "分页请求参数")
public class PageRequest<T> {

    @Schema(description = "起始页")
    private Integer pageNum;

    @Schema(description = "每页显示记录数")
    private Integer pageSize;

    @Schema(description = "排序")
    private String orderBy;

    private T data;
}
