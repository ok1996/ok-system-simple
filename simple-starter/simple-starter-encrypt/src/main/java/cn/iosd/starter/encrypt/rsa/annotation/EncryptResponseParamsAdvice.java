package cn.iosd.starter.encrypt.rsa.annotation;

import cn.iosd.starter.encrypt.rsa.properties.RsaProperties;
import cn.iosd.starter.encrypt.rsa.utils.RsaUtils;
import cn.iosd.utils.jackson.JsonMapperThreadLocal;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Optional;

/**
 * 加密响应参数
 *
 * @author ok1996
 */
@ControllerAdvice
@ConditionalOnProperty(name = "simple.encrypt.rsa.secureParams.enabled", havingValue = "true", matchIfMissing = true)
public class EncryptResponseParamsAdvice implements ResponseBodyAdvice<Object> {
    private static final Logger log = LoggerFactory.getLogger(EncryptResponseParamsAdvice.class);

    @Autowired
    private RsaProperties rsaProperties;

    /**
     * 方法上包含注解EncryptResponseParams则进行拦截
     *
     * @param returnType    返回类型
     * @param converterType 转换器类型
     * @return true or false
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.hasMethodAnnotation(EncryptResponseParams.class);
    }

    /**
     * 对响应体进行加密处理
     *
     * @param body                  响应体
     * @param returnType            返回类型
     * @param selectedContentType   选择的媒体类型
     * @param selectedConverterType 选择的转换器类型
     * @param request               请求对象
     * @param response              响应对象
     * @return 加密后的响应体
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType
            , ServerHttpRequest request, ServerHttpResponse response) {
        return encrypt(body);
    }

    private Object encrypt(Object responseObj) {
        return Optional.ofNullable(rsaProperties.getPublicKey())
                .filter(StringUtils::isNotBlank)
                .map(pubKey -> {
                    try {
                        String content = JsonMapperThreadLocal.getObjectMapper().writeValueAsString(responseObj);
                        return RsaUtils.encrypt(content, pubKey);
                    } catch (JsonProcessingException e) {
                        log.error("Failed to convert object to JSON string: {}", e.getMessage());
                    } catch (Exception e) {
                        log.error("EncryptResponseParamsAdvice error: {}", e.getMessage());
                    }
                    return responseObj;
                })
                .orElseGet(() -> {
                    throw new IllegalArgumentException("publicKey must not be null");
                });
    }

}
