package cn.iosd.starter.datasource.domain;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Objects;

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

    /**
     * 转换MybatisPlus的分页对象
     *
     * @return Page
     */
    public Page<T> toPage() {
        Objects.requireNonNull(pageNum, "Page number cannot be null");
        Objects.requireNonNull(pageSize, "Page size cannot be null");

        Page<T> page = new Page<>(pageNum, pageSize);
        page.setOrders(orders);
        return page;
    }

    /**
     * 转换MybatisPlus的条件构造对象
     *
     * @return LambdaQueryWrapper
     */
    public LambdaQueryWrapper<T> toWrapper() {
        return Wrappers.lambdaQuery(data);
    }
}
