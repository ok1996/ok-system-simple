package cn.iosd.starter.datasource.domain;

/**
 * 分页请求参数
 *
 * @author ok1996
 */
public class PageRequest<T> {
    /**
     * 起始页
     */
    private Integer pageNum;
    /**
     * 每页显示记录数
     */
    private Integer pageSize;
    /**
     * 排序
     */
    private String orderBy;

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

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}