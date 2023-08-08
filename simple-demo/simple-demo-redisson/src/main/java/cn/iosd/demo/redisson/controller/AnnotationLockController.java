package cn.iosd.demo.redisson.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.iosd.starter.redisson.annotation.DistributedLock;
import cn.iosd.starter.web.domain.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ok1996
 */
@Slf4j
@RestController
@Tag(name = "分布式锁-注解模式-测试请求")
@RequestMapping("/simple-demo-redisson/annotationLock")
public class AnnotationLockController {

    /**
     * 商品库存
     */
    public static volatile Integer TOTAL = 10000;

    @Operation(summary = "库存自减")
    @GetMapping("decrement")
    @DistributedLock(value = "demo-redisson:decrement", leaseTime = 105)
    public Response<?> decrement() {
        if (TOTAL > 0) {
            TOTAL--;
        }
        log.info("===/annotationLock/decrement注解模式=== 减完库存后,当前库存===" + TOTAL);
        return Response.ok();
    }

    @Operation(summary = "库存自减-测试异常")
    @GetMapping("decrementException")
    @DistributedLock(value = "demo-redisson:decrementException", leaseTime = 105)
    public void decrementException() throws Exception {
        if (TOTAL > 0) {
            TOTAL--;
        }
        log.info("===/annotationLock/decrementException注解模式=== 减完库存后,当前库存===" + TOTAL);
        throw new Exception("异常测试");
    }
}
