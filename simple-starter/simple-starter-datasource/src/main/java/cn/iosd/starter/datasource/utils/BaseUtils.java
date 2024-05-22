package cn.iosd.starter.datasource.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author ok1996
 */
public class BaseUtils {

    /**
     * 通过反射调用实体类对象中的指定方法，设置指定的值。
     *
     * @param entity     需要设置值的实体类对象
     * @param methodName 需要调用的方法名
     * @param value      需要设置的值
     * @param valueType  value参数的类型
     * @param <T>        实体类对象的类型
     * @param <V>        value参数的类型
     */
    public static <T, V> void setValue(T entity, String methodName, V value, Class<V> valueType) {
        try {
            Method method = entity.getClass().getMethod(methodName, valueType);
            method.invoke(entity, value);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            // T实体不存在方法，不做处理
        }
    }
}
