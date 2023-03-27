package cn.iosd.starter.datasource.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.iosd.starter.datasource.domain.PageRequest;

/**
 * @author ok1996
 */
public class DsConvertUtil {
    /**
     * 将自定义的分页对象PageRequest转换为mybatisPlus的分页对象Page
     *
     * @param pageRequest 自定义分页对象
     * @param <T>         查询条件数据类型
     * @return mybatisPlus的分页对象
     */
    public static <T> Page<T> page(PageRequest<T> pageRequest) {
        Page<T> page = new Page<>(pageRequest.getPageNum(), pageRequest.getPageSize());
        page.setOrders(pageRequest.getOrders());
        return page;
    }
}
