package cn.iosd.starter.redisson.utils;

import cn.iosd.starter.redisson.domain.MethodContext;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.util.DigestUtils;

import java.util.function.Supplier;

/**
 * @author ok1996
 */
public class LockUtil {
    /**
     * 动态参数值未获取到时的默认值
     */
    private static final String PARAM_ABSENT_DEFAULT_VALUE = "DV";

    /**
     * 生成key
     * <pre>
     *    值： includePointMd5?
     *          前缀+固定值+动态参数值+md5.get() :
     *          前缀+固定值+动态参数值
     *    注意：调用方法名称应避免重复
     * </pre>
     *
     * @param point           切点信息
     * @param prefix          key前缀
     * @param value           key固定值
     * @param param           key动态参数值
     * @param includePointMd5 key后缀：是否拼接MD5（值：从切点获取方法参数和方法名称生成对象，转为md5）
     * @return key值
     */
    public static String generateKey(ProceedingJoinPoint point, String prefix, String value, String param, boolean includePointMd5) {
        MethodContext context = SpElUtil.getArgMap(point);
        if (includePointMd5) {
            Supplier<String> md5 = () -> DigestUtils.md5DigestAsHex(context.toString().getBytes());
            String dynamicPart = StringUtils.isEmpty(param)
                    ? md5.get()
                    : SpElUtil.analytical(param, context.argMap(), String.class, PARAM_ABSENT_DEFAULT_VALUE) + ":" + md5.get();

            return prefix + value + ":" + dynamicPart;
        }
        if (StringUtils.isEmpty(param)) {
            return prefix + value;
        }
        String dynamicPart = SpElUtil.analytical(param, context.argMap(), String.class, PARAM_ABSENT_DEFAULT_VALUE);
        return prefix + value + ":" + dynamicPart;
    }


}
