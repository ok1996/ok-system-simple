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
     * 忽略字段
     */
    private static final String SERIAL_VERSION_UID_FIELD  = "serialVersionUID";

    /**
     * 将通用实体类查询条件转换成MyBatis Plus的QueryWrapper
     *
     * @param entity 通用实体类查询条件
     * @param <T>    实体类类型
     * @return MyBatis Plus的QueryWrapper
     */
    public static <T> QueryWrapper<T> queryWrapperEqual(T entity) {
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (SERIAL_VERSION_UID_FIELD.equals(field.getName())) {
                continue;
            }
            try {
                field.setAccessible(true);
                Object value = field.get(entity);
                if (value != null && StringUtils.isNotBlank(value.toString())) {
                    wrapper.eq(camelToUnderline(field.getName()), value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return wrapper;
    }


    /**
     * 使用正则表达式将驼峰式的字符串转换为下划线式
     *
     * @param param
     * @return
     */
    public static String camelToUnderline(String param) {
        if (StringUtils.isBlank(param)) {
            return "";
        }
        return param.replaceAll("[A-Z]", "_$0").toLowerCase();
    }

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
