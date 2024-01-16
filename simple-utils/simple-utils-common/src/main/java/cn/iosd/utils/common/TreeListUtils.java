package cn.iosd.utils.common;

import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 将列表转换为树形结构工具类
 *
 * @author ok1996
 */
public class TreeListUtils {

    /**
     * 将原始列表转换为树形结构的工具方法
     *
     * @param <T>               列表元素类型
     * @param <D>               ID元素类型
     * @param origList          原始列表
     * @param childrenFieldName 子节点列表在实体中的字段名称
     * @param isRootPredicate   判断是否为根节点的断言条件
     * @param getId             获取实体的ID的函数
     * @param getParentId       获取实体的父ID的函数
     * @return 转换后的树形结构列表
     */
    public static <T, D> List<T> convert(List<T> origList, String childrenFieldName, Predicate<D> isRootPredicate
            , Function<T, D> getId, Function<T, D> getParentId) {
        return convert(origList, childrenFieldName, isRootPredicate, getId, getParentId
                , null, null, null);
    }

    /**
     * 将原始列表转换为树形结构的工具方法
     * <p>
     * 并将关联对象添加进去树形结构
     * <p/>
     *
     * @param <T>               列表元素类型
     * @param <V>               关联数据元素类型
     * @param <D>               ID元素类型
     * @param origList          原始列表
     * @param childrenFieldName 子节点列表在实体中的字段名称
     * @param isRootPredicate   判断是否为根节点的断言条件
     * @param getId             获取实体的ID的函数
     * @param getParentId       获取实体的父ID的函数
     * @param idData            ID与关联数据的映射
     * @param dataFieldName     关联数据字段在实体中的名称
     * @param getDataRelationId 获取关联数据与实体关系的ID的函数
     * @return 转换后的树形结构列表
     */
    public static <T, V, D> List<T> convert(List<T> origList, String childrenFieldName, Predicate<D> isRootPredicate
            , Function<T, D> getId, Function<T, D> getParentId
            , Map<D, V> idData, String dataFieldName, Function<T, D> getDataRelationId) {
        // 是否需要添加关联数据
        boolean addDataBool = !(ObjectUtils.isEmpty(dataFieldName) || ObjectUtils.isEmpty(idData));

        Map<D, T> idMaps = new HashMap<>(origList.size());
        // 预先遍历一次原始列表，将id赋值给idMaps
        for (T entity : origList) {
            D id = getId.apply(entity);
            if (ObjectUtils.isEmpty(id)) {
                throw new IllegalArgumentException("存在id为空的数据");
            }
            idMaps.put(id, entity);
        }

        List<T> result = new ArrayList<>();
        for (T entity : origList) {
            if (addDataBool) {
                D dataId = getDataRelationId.apply(entity);
                setFieldValue(entity, dataFieldName, idData.get(dataId));
            }
            D parentId = getParentId.apply(entity);
            if (isRootPredicate.test(parentId)) {
                result.add(entity);
            } else {
                T parentEntity = idMaps.get(parentId);
                if (parentEntity != null) {
                    setChildrenValue(childrenFieldName, entity, parentEntity);
                } else {
                    result.add(entity);
                }
            }
        }
        return result;
    }


    /**
     * 设置实体的子节点
     *
     * @param childrenFieldName 子节点字段名
     * @param entity            当前实体
     * @param parentEntity      父实体
     * @param <T>               实体类型
     */
    private static <T> void setChildrenValue(String childrenFieldName, T entity, T parentEntity) {
        // 获取父实体的子节点列表，如果为空则创建一个新列表
        List<T> children = Optional.ofNullable(getFieldValue(parentEntity, childrenFieldName))
                .map(value -> (List<T>) value)
                .orElse(new ArrayList<>());
        // 将当前实体添加到父实体的子节点列表中
        children.add(entity);
        setFieldValue(parentEntity, childrenFieldName, children);
    }

    /**
     * 获取实体的字段值
     *
     * @param entity    实体
     * @param fieldName 字段名称
     * @param <T>       实体类型
     * @return 字段的值
     */
    private static <T> Object getFieldValue(T entity, String fieldName) {
        try {
            Field field = entity.getClass().getDeclaredField(fieldName);
            ReflectionUtils.makeAccessible(field);
            return field.get(entity);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalArgumentException("字段名称[" + fieldName + "]不存在", e);
        }
    }

    /**
     * 设置实体的字段值
     *
     * @param entity    实体
     * @param fieldName 字段名称
     * @param value     字段的值
     * @param <T>       实体类型
     */
    private static <T> void setFieldValue(T entity, String fieldName, Object value) {
        try {
            Field field = entity.getClass().getDeclaredField(fieldName);
            ReflectionUtils.makeAccessible(field);
            field.set(entity, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalArgumentException("字段名称[" + fieldName + "]不存在", e);
        }
    }
}
