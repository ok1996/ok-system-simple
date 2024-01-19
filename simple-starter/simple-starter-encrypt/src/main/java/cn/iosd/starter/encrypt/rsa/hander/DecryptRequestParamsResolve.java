package cn.iosd.starter.encrypt.rsa.hander;

import cn.iosd.starter.encrypt.rsa.annotation.DecryptRequestParams;
import cn.iosd.starter.encrypt.rsa.properties.RsaProperties;
import cn.iosd.starter.encrypt.rsa.utils.CheckAnnotationUtils;
import cn.iosd.starter.encrypt.rsa.utils.RsaUtils;
import cn.iosd.utils.jackson.JsonMapperThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Map;

/**
 * 解密请求参数
 * <p>
 * 对 URL查询参数 进行处理
 *
 * @author ok1996
 */
public class DecryptRequestParamsResolve implements HandlerMethodArgumentResolver {
    @Autowired
    private RsaProperties rsaProperties;

    /**
     * URL查询参数的请求字段
     */
    @Value("${simple.encrypt.rsa.secureParams.urlField:encryptedData}")
    private String urlField;

    /**
     * 是否启用将所有使用Mapping注解的接口加解密
     */
    @Value("${simple.encrypt.rsa.secureParams.all-controller.mapping.enabled:false}")
    private boolean allControllerMappingEnabled;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return CheckAnnotationUtils.check(allControllerMappingEnabled, parameter, DecryptRequestParams.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // 对查询参数进行解密和处理
        String privateKey = rsaProperties.getPrivateKey();
        Assert.notNull(privateKey, "privateKey must not be null");

        Map<String, String[]> parameterMap = webRequest.getParameterMap();
        Assert.notNull(parameterMap.get(urlField), "Request field of URL query parameters cannot be empty：" + urlField);

        String encryptedContent = parameterMap.get(urlField)[0];
        String contentsDecrypt = RsaUtils.decrypt(encryptedContent, privateKey);

        RsaUtils.timestampValidation(rsaProperties.getTimestampValidation(), contentsDecrypt);

        return JsonMapperThreadLocal.getObjectMapper().readValue(contentsDecrypt, parameter.getParameterType());
    }
}
