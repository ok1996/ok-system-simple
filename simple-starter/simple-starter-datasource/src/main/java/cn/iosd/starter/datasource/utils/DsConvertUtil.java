package cn.iosd.starter.datasource.utils;

import cn.iosd.starter.datasource.domain.PageRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

/**
 * @author ok1996
 */
public class DsConvertUtil {
    /**
     * 将自定义的分页对象PageRequest<T>转换为mybatisPlus的分页对象Page<T>
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
