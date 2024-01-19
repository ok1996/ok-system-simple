package cn.iosd.starter.encrypt.rsa.hander;

import cn.iosd.starter.encrypt.rsa.annotation.DecryptRequestParams;
import cn.iosd.starter.encrypt.rsa.properties.RsaProperties;
import cn.iosd.starter.encrypt.rsa.utils.CheckAnnotationUtils;
import cn.iosd.starter.encrypt.rsa.utils.RsaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.stream.Collectors;

/**
 * 解密请求参数
 * <p>
 * 对 RequestBody 进行处理
 *
 * @author ok1996
 */
@ControllerAdvice
@ConditionalOnProperty(name = "simple.encrypt.rsa.secureParams.enabled", havingValue = "true", matchIfMissing = true)
public class DecryptRequestParamsAdvice implements RequestBodyAdvice {

    private static final Logger log = LoggerFactory.getLogger(DecryptRequestParamsAdvice.class);

    @Autowired
    private RsaProperties rsaProperties;

    /**
     * 是否启用将所有使用Mapping注解的接口加解密
     */
    @Value("${simple.encrypt.rsa.secureParams.all-controller.mapping.enabled:false}")
    private boolean allControllerMappingEnabled;

    @Override
    public boolean supports(MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return CheckAnnotationUtils.check(allControllerMappingEnabled, parameter, DecryptRequestParams.class);
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        String privateKey = rsaProperties.getPrivateKey();
        Assert.notNull(privateKey, "privateKey must not be null");

        String content = new BufferedReader(new InputStreamReader(inputMessage.getBody()))
                .lines().collect(Collectors.joining(System.lineSeparator()));
        String contentsDecrypt = "";
        try {
            contentsDecrypt = RsaUtils.decrypt(content, privateKey);
        } catch (Exception e) {
            log.error("RsaUtils解密发生异常:", e);
        }

        RsaUtils.timestampValidation(rsaProperties.getTimestampValidation(), contentsDecrypt);

        return new DecryptRequestParamsInputMessage(inputMessage, contentsDecrypt);
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    private static class DecryptRequestParamsInputMessage implements HttpInputMessage {
        private final HttpHeaders headers;
        private final InputStream body;

        public DecryptRequestParamsInputMessage(HttpInputMessage inputMessage, String contentsDecrypt) {
            this.headers = inputMessage.getHeaders();
            this.body = new ByteArrayInputStream(contentsDecrypt.getBytes());
        }

        @Override
        public InputStream getBody() throws IOException {
            return body;
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }
    }
}
