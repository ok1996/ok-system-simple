package cn.iosd.starter.datasource.domain;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.List;

/**
 * 分页响应参数
 *
 * @author ok1996
 */
@Data
public class PageResponse<T> {
    /**
     * 当前页
     */
    private int pageNum;
    /**
     * 每页显示记录数
     */
    private int pageSize;
    /**
     * 记录总数
     */
    private long total;
    /**
     * 数据列表
     */
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