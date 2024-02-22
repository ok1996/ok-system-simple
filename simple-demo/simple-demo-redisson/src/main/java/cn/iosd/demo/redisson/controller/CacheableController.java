package cn.iosd.demo.redisson.controller;

import cn.iosd.demo.redisson.service.CacheableService;
import cn.iosd.starter.web.domain.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ok1996
 */
@Slf4j
@RestController
@Tag(name = "缓存测试")
@RequestMapping("/simple-demo-redisson/cacheable")
public class CacheableController {

    @Autowired
    private CacheableService service;

    @Operation(summary = "缓存注解测试使用cacheName")
    @GetMapping("annotateTestCacheName")
    public Response<String> annotateTestCacheName(String keyName, Integer cycles) {
        for (int i = 1; i <= cycles; i++) {
            service.annotateTestCacheNameTenMinutes(keyName);
            service.annotateTestCacheNameTenSecond(keyName);
        }
        return Response.ok(keyName);
    }
}
