package cn.iosd.starter.redisson.utils;

import cn.iosd.starter.redisson.domain.MethodContext;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

import java.util.function.Supplier;

/**
 * @author ok1996
 */
public class KeyUtil {
    private static final Logger log = LoggerFactory.getLogger(KeyUtil.class);

    /**
     * 静态参数值为空时的默认值： STATIC_DEFAULT_VALUE
     */
    private static final String STATIC_ABSENT_DEFAULT_VALUE = "SDV";

    /**
     * 动态参数值未获取到时的默认值：PARAM_DEFAULT_VALUE
     */
    private static final String PARAM_ABSENT_DEFAULT_VALUE = "PDV";

    /**
     * 生成key
     * <pre>
     *    值： includePointMd5?
     *          固定值+动态参数值+md5.get() :
     *          固定值+动态参数值
     * </pre>
     *
     * @param point           切点信息
     * @param value           key固定值
     * @param param           key动态参数值
     * @param includePointMd5 key后缀：是否拼接MD5（值：从切点获取方法参数和方法名称生成对象，转为md5）
     * @return key值
     * @throws IllegalArgumentException 如果includePointMd5为false且value和param均为空
     */
    public static String generate(ProceedingJoinPoint point, String value, String param, boolean includePointMd5) {
        MethodContext context = SpElUtil.getArgMap(point);
        String staticPart = StringUtils.isEmpty(value) ? STATIC_ABSENT_DEFAULT_VALUE : value;
        String dynamicPart = SpElUtil.analytical(param, context.argMap(), PARAM_ABSENT_DEFAULT_VALUE);
        String key = staticPart + ":" + dynamicPart;
        if (includePointMd5) {
            Supplier<String> md5 = () -> DigestUtils.md5DigestAsHex(context.toString().getBytes());
            return key + ":" + md5.get();
        } else {
            if (STATIC_ABSENT_DEFAULT_VALUE.equals(staticPart) && PARAM_ABSENT_DEFAULT_VALUE.equals(dynamicPart)) {
                log.error("Please verify that both the static part and the dynamic part yield empty values." +
                        "The current default return value is set to [{}]", key);
            }
            return key;
        }
    }


}
