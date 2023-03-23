package cn.iosd.starter.datasource.domain;


import com.baomidou.mybatisplus.core.metadata.OrderItem;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * 分页请求参数
 *
 * @author ok1996
 */
public class PageRequest<T> {

    @Schema(description = "起始页")
    private Integer pageNum;

    @Schema(description = "每页显示记录数")
    private Integer pageSize;

    @Schema(description = "排序")
    private List<OrderItem> orders;

    @Schema(description = "查询条件参数")
    private T data;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<OrderItem> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderItem> orders) {
        this.orders = orders;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
