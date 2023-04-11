package cn.iosd.starter.encode.rsa.annotation;

import cn.iosd.starter.encode.rsa.properties.RsaProperties;
import cn.iosd.starter.encode.rsa.utils.RsaUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
 *
 * @author ok1996
 */
@ControllerAdvice
@ConditionalOnProperty(name = "simple.encode.rsa.secureParams.enabled", havingValue = "true", matchIfMissing = true)
public class DecryptRequestParamsAdvice implements RequestBodyAdvice {
    @Autowired
    private RsaProperties rsaProperties;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return methodParameter.getMethodAnnotation(DecryptRequestParams.class) != null;
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
            throw new RuntimeException(e);
        }

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
