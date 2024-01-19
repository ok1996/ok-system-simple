package cn.iosd.starter.encrypt.rsa.utils;

import cn.iosd.starter.encrypt.rsa.annotation.DecryptRequestParams;
import cn.iosd.starter.encrypt.rsa.annotation.EncryptResponseParams;
import cn.iosd.starter.encrypt.rsa.annotation.UnEncrypted;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ok1996
 */
public class CheckAnnotationUtils {

    private static final Set<Class<? extends Annotation>> MAPPING_ANNOTATIONS = new HashSet<>(Arrays.asList(
            RequestMapping.class, GetMapping.class, PostMapping.class, PutMapping.class, DeleteMapping.class));

    /**
     * 检查是否具有指定类型的注解，并根据全局加密开关决定是否考虑全局加密
     *
     * @param isGlobalCryptoEnabled 全局加密开关，true表示开启，false表示关闭
     * @param parameter             方法参数
     * @param annotationType        额外要检查的注解类型
     * @param <A>                   注解类型
     * @return 如果具有指定类型的注解则返回true，否则返回false
     */
    public static <A extends Annotation> boolean check(boolean isGlobalCryptoEnabled, MethodParameter parameter, Class<A> annotationType) {
        if (hasAnnotation(parameter, UnEncrypted.class)) {
            return false;
        }
        if (hasAnnotation(parameter, DecryptRequestParams.class) || hasAnnotation(parameter, EncryptResponseParams.class)) {
            //若接口上已指定注解则直接进入判断状态
            return hasAnnotation(parameter, annotationType);
        }
        return isGlobalCryptoEnabled
                ? hasMappingAnnotation(parameter.getMethodAnnotations()) || hasAnnotation(parameter, annotationType)
                : hasAnnotation(parameter, annotationType);
    }

    private static boolean hasAnnotation(MethodParameter parameter, Class<? extends Annotation> annotationType) {
        return parameter.hasMethodAnnotation(annotationType);
    }

    private static boolean hasMappingAnnotation(Annotation[] annotations) {
        return Arrays.stream(annotations).anyMatch(CheckAnnotationUtils::isMappingAnnotation);
    }

    /**
     * 检查给定的注解是否为映射注解
     *
     * @param annotation 要检查的注解
     * @return 如果是映射注解则返回 true，否则返回 false
     */
    private static boolean isMappingAnnotation(Annotation annotation) {
        return MAPPING_ANNOTATIONS.contains(annotation.annotationType());
    }
}
