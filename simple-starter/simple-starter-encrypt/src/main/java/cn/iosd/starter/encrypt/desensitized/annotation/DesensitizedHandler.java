package cn.iosd.starter.encrypt.desensitized.annotation;

import cn.iosd.starter.encrypt.desensitized.utils.DesensitizedUtils;
import cn.iosd.starter.encrypt.desensitized.vo.SensitiveRule;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Function;

/**
 * @author ok1996
 */
@Aspect
@Component
@ConditionalOnProperty(name = "simple.encrypt.desensitized.enabled", havingValue = "true", matchIfMissing = true)
public class DesensitizedHandler {

    @Around("@annotation(cn.iosd.starter.encrypt.desensitized.annotation.Desensitized)")
    public Object idempotent(final ProceedingJoinPoint point) throws Throwable {
        Object responseObj = point.proceed();
        desensitizeAndEncryptObjects(responseObj);
        return responseObj;
    }

    /**
     * 将对象进行脱敏加密
     *
     * @param responseObj 对象
     * @throws IllegalAccessException
     */
    private void desensitizeAndEncryptObjects(Object responseObj) throws IllegalAccessException {
        if (responseObj instanceof List<?>) {
            for (Object singleObj : (List<?>) responseObj) {
                desensitizeAndEncryptObjects(singleObj);
            }
        }

        Field[] fields = responseObj.getClass().getDeclaredFields();
        for (Field field : fields) {
            SensitiveField sensitiveField = field.getAnnotation(SensitiveField.class);
            if (sensitiveField != null) {
                field.setAccessible(true);
                Object fieldValue = field.get(responseObj);
                String deValue = desensitize(String.valueOf(fieldValue), sensitiveField.rule(),
                        sensitiveField.prefixLen(), sensitiveField.suffixLen());
                field.set(responseObj, deValue);
            } else if (field.isAnnotationPresent(SensitiveEntity.class)) {
                field.setAccessible(true);
                Object fieldValue = field.get(responseObj);
                desensitizeAndEncryptObjects(fieldValue);
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
        String deStr;
        switch (sensitiveRule) {
            case CUSTOM_BROADSIDE_CLEAR_TEXT:
                deStr = DesensitizedUtils.desValue(value, prefixLen, suffixLen);
                break;
            case CUSTOM_BROADSIDE_MASK_TEXT:
                deStr = DesensitizedUtils.maskValue(value, prefixLen, suffixLen);
                break;
            default:
                deStr = DesensitizedUtils.DESENSITIZE_MAP.getOrDefault(sensitiveRule, Function.identity()).apply(value);
                break;
        }
        return deStr;
    }
}
