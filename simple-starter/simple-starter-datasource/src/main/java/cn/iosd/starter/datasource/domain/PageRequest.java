package cn.iosd.starter.datasource.domain;

import lombok.Data;

/**
 * 分页请求参数
 *
 * @author ok1996
 */
@Data
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
}
