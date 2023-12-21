package cn.iosd.starter.web.handler;

import cn.iosd.starter.web.domain.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * 全局异常处理器
 *
 * @author ok1996
 */
@RestControllerAdvice
@ConditionalOnProperty(prefix = "simple.handler.exception", name = "enabled"
        , havingValue = "true", matchIfMissing = true)
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理系统异常
     *
     * @param e       异常对象
     * @param request HTTP请求对象
     * @return 返回响应结果
     */
    @ExceptionHandler(Exception.class)
    public Response<String> handleException(Exception e, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        log.error("请求地址'{}'，发生系统异常: {}", requestUri, e.getMessage(), e);
        return Response.fail(e.getMessage());
    }

    /**
     * 资源缺失异常
     *
     * @param e       异常对象
     * @param request HTTP请求对象
     * @return 返回响应结果
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public Response<String> handleNoResourceFoundException(NoResourceFoundException e,
                                                           HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        log.debug("请求地址'{}'，资源缺失异常: {}", requestUri, e.getMessage(), e);
        return Response.fail(e.getMessage());
    }

    /**
     * 请求方式不支持
     *
     * @param e       异常对象
     * @param request HTTP请求对象
     * @return 返回响应结果
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Response<String> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
                                                                HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        log.error("请求地址'{}',不支持'{}'请求", requestUri, e.getMethod());
        return Response.fail(e.getMessage());
    }
}
