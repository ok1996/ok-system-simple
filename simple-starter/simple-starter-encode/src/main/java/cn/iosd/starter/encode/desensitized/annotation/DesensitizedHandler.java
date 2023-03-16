package cn.iosd.starter.encode.desensitized.annotation;

import cn.iosd.starter.encode.desensitized.utils.DesensitizedUtils;
import cn.iosd.starter.encode.desensitized.vo.SensitiveRule;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ok1996
 */
@Aspect
@Component
@ConditionalOnProperty(name = "simple.encode.desensitized.enabled", havingValue = "true", matchIfMissing = true)
public class DesensitizedHandler {

    @Around("@annotation(cn.iosd.starter.encode.desensitized.annotation.Desensitized)")
    public Object idempotent(final ProceedingJoinPoint point) throws Throwable {
        Object responseObj = point.proceed();
        if (responseObj instanceof ArrayList<?>) {
            for (Object singleObj : (List<?>) responseObj) {
                singleField(singleObj);
            }
        } else {
            singleField(responseObj);
        }
        return responseObj;
    }

    /**
     * 对单体对象进行脱敏加密
     *
     * @param singleObj 单体对象
     * @throws Throwable
     */
    private void singleField(Object singleObj) throws Throwable {
        Field[] fields = singleObj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(SensitiveField.class)) {
                if (!field.canAccess(singleObj)) {
                    field.setAccessible(true);
                }
                Object obj = field.get(singleObj);
                SensitiveField sensitiveField = field.getAnnotation(SensitiveField.class);
                String deValue = desensitize(String.valueOf(obj), sensitiveField.rule()
                        , sensitiveField.prefixLen(), sensitiveField.suffixLen());
                field.set(singleObj, deValue);
            }
        }
    }

    /**
     * 对字段进行脱敏
     *
     * @param value         原始值
     * @param sensitiveRule 脱敏规则
     * @param prefixLen     自定义规则-左侧需要保留几位明文字段
     * @param suffixLen     自定义规则-右侧需要保留几位明文字段
     * @return
     */
    private String desensitize(String value, SensitiveRule sensitiveRule, int prefixLen, int suffixLen) {
        String deStr = null;
        switch (sensitiveRule) {
            case CHINESE_NAME -> deStr = DesensitizedUtils.chineseName(value);
            case ID_CARD -> deStr = DesensitizedUtils.idCardNum(value);
            case FIXED_PHONE -> deStr = DesensitizedUtils.fixedPhone(value);
            case MOBILE_PHONE -> deStr = DesensitizedUtils.mobilePhone(value);
            case ADDRESS -> deStr = DesensitizedUtils.address(value);
            case EMAIL -> deStr = DesensitizedUtils.email(value);
            case BANK_CARD -> deStr = DesensitizedUtils.bankCard(value);
            case PASSWORD -> deStr = DesensitizedUtils.password(value);
            case CUSTOM -> deStr = DesensitizedUtils.desValue(value, prefixLen, suffixLen);
            default -> deStr = value;
        }
        return deStr;
    }
}
