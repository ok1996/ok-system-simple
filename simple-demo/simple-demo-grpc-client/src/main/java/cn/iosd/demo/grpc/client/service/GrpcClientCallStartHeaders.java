package cn.iosd.demo.grpc.client.service;

import cn.iosd.starter.grpc.client.interceptor.ClientCallStartHeaders;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

/**
 * @author ok1996
 */
@Component
public class GrpcClientCallStartHeaders implements ClientCallStartHeaders {
    @Override
    public Map<String, String> headers() {
        return Collections.singletonMap("token", "ces1");
    }
}
