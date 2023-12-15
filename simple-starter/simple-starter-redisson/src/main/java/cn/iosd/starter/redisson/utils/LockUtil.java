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
     * 生成用于获取锁的名称
     *
     * @param point  切入点对象，包含有关被拦截方法的信息
     * @param prefix KEY前缀
     * @param value  KEY固定部分
     * @param param  KEY动态参数部分，可以为空
     * @return 生成的锁名称字符串
     */
    public static String generateLockName(ProceedingJoinPoint point, String prefix, String value, String param) {
        MethodContext context = SpElUtil.getArgMap(point);
        Supplier<String> md5 = () -> DigestUtils.md5DigestAsHex(SpElUtil.getArgMap(point).toString().getBytes());

        String dynamicPart = StringUtils.isEmpty(param)
                ? md5.get()
                : SpElUtil.analytical(param, context.argMap(), String.class, context.methodName()) + ":" + md5.get();

        return prefix + value + ":" + dynamicPart;
    }

}
