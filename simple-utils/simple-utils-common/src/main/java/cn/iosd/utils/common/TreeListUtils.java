package cn.iosd.utils.common;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
     * 将原始列表转换为树形结构，没有父级关联的数据视为一级节点
     *
     * @param origList          原始列表
     * @param idFieldName       id字段名
     * @param parentIdFieldName parentId字段名
     * @param childrenFieldName children字段名
     * @param isRootPredicate   判断是否为根节点的条件
     * @param <T>               实体类型
     * @return 树形结构的结果列表
     */
    public static <T> List<T> convert(List<T> origList, String idFieldName, String parentIdFieldName
            , String childrenFieldName, Predicate<String> isRootPredicate) {
        Map<String, T> idMaps = new HashMap<>(origList.size());
        // 获取字段值的方法
        Function<T, String> getId = entity -> Objects.toString(getFieldValue(entity, idFieldName), "");
        // 预先遍历一次原始列表，将id赋值给idMaps
        for (T entity : origList) {
            String id = getId.apply(entity);
            if (id.isEmpty()) {
                throw new IllegalArgumentException("存在id为空的数据");
            }
            idMaps.put(id, entity);
        }

        List<T> result = new ArrayList<>();
        Function<T, String> getParentId = entity -> Objects.toString(getFieldValue(entity, parentIdFieldName), "");
        for (T entity : origList) {
            String parentId = getParentId.apply(entity);
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
     * 将原始列表转换为树形结构
     * <p>
     * 原始列表数据要求：一级数据在二级数据前、以此类推。<br/>
     * 若原始列表不符合要求：则视为二级在一级前的数据是没有父级关联
     * </p>
     *
     * @param origList          原始列表
     * @param idFieldName       id字段名
     * @param parentIdFieldName parentId字段名
     * @param childrenFieldName children字段名
     * @param isRootPredicate   判断是否为根节点的条件
     * @param rootCandidateData 是否将没有父级关联的数据添加为一级节点
     * @param <T>               实体类型
     * @return 树形结构的结果列表
     */
    public static <T> List<T> convertBySequentialGrade(List<T> origList, String idFieldName, String parentIdFieldName
            , String childrenFieldName, Predicate<String> isRootPredicate, Boolean rootCandidateData) {
        Map<String, T> idMaps = new HashMap<>(origList.size());
        List<T> result = new ArrayList<>();

        // 获取字段值的方法
        Function<T, String> getId = entity -> Objects.toString(getFieldValue(entity, idFieldName), "");
        Function<T, String> getParentId = entity -> Objects.toString(getFieldValue(entity, parentIdFieldName), "");

        for (T entity : origList) {
            String id = getId.apply(entity);
            String parentId = getParentId.apply(entity);
            if (id.isEmpty()) {
                throw new IllegalArgumentException("存在id为空的数据");
            }
            idMaps.put(id, entity);
            if (isRootPredicate.test(parentId)) {
                result.add(entity);
            } else {
                T parentEntity = idMaps.get(parentId);
                if (parentEntity != null) {
                    setChildrenValue(childrenFieldName, entity, parentEntity);
                } else if (rootCandidateData) {
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


