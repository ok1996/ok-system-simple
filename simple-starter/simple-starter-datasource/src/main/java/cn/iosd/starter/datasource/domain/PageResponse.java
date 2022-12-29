package cn.iosd.starter.datasource.domain;

import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author ok1996
 */
@Data
@Schema(description = "分页响应参数")
public class PageResponse<T> {
    @Schema(description = "当前页")
    private int pageNum;

    @Schema(description = "每页显示记录数")
    private int pageSize;

    @Schema(description = "记录总数")
    private long total;

    @Schema(description = "数据列表")
    private List<T> list;

    public static <T> PageResponse<T> getPage(List<T> list) {
        PageResponse rspData = new PageResponse();
        PageInfo pageInfo = new PageInfo(list);
        rspData.setList(list);
        rspData.setTotal(pageInfo.getTotal());
        rspData.setPageSize(pageInfo.getPageSize());
        rspData.setPageNum(pageInfo.getPageNum());
        return rspData;
    }
}
