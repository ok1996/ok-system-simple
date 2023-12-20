package cn.iosd.demo.redisson.controller;

import cn.iosd.demo.redisson.service.AnnotationTestService;
import cn.iosd.demo.redisson.vo.PersonVo;
import cn.iosd.starter.redisson.annotation.DistributedIdempotent;
import cn.iosd.starter.web.domain.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ok1996
 */
@Slf4j
@RestController
@Tag(name = "分布式幂等-注解模式-测试请求")
@RequestMapping("/simple-demo-redisson/annotationIdempotent")
public class AnnotationIdempotentController {
    /**
     * 商品库存
     */
    public static volatile Integer TOTAL1 = 10000;
    public static volatile Integer TOTAL2 = 10000;
    public static volatile Integer TOTAL3 = 10000;
    @Autowired
    private AnnotationTestService service;

    @Operation(summary = "库存自减")
    @GetMapping("decrement")
    @DistributedIdempotent
    public Response<?> decrement() {
        if (TOTAL1 > 0) {
            TOTAL1--;
        }
        log.info("===/annotationIdempotent/decrement注解模式=== 减完库存后,当前库存===" + TOTAL1);
        return Response.ok();
    }

    @Operation(summary = "库存自减-请求参数变量-在业务执行结束后解锁")
    @GetMapping("decrementReqPara")
    @DistributedIdempotent(value = "drp", message = "请求重复！", expireTime = 5)
    public Response<?> decrementReqPara(String keyName) {
        if (TOTAL2 > 0) {
            TOTAL2--;
        }
        log.info("===/annotationIdempotent/decrementReqPara注解模式=== 减完库存后,当前库存===" + TOTAL2);
        return Response.ok();
    }

    @Operation(summary = "库存自减-请求参数变量-等待expireTime自动过期后自动解锁")
    @PostMapping("decrementReqPara/executionFinishedUnlock")
    @DistributedIdempotent(param = "#vo.id", includePointMd5 = false, message = "请求重复！", expireTime = 5, executionFinishedUnlock = false)
    public Response<?> decrementReqParaExecutionFinishedUnlock(@RequestBody PersonVo vo) {
        if (TOTAL3 > 0) {
            TOTAL3--;
        }
        log.info("===/annotationIdempotent/decrementReqPara/executionFinishedUnlock注解模式=== 减完库存后,当前库存===" + TOTAL3);
        return Response.ok();
    }

    @Operation(summary = "库存自减-异常测试")
    @GetMapping("decrementException")
    public Response<?> decrementException(String keyName) {
        Integer cycles = 10;
        for (int i = 1; i <= cycles; i++) {
            service.distributedIdempotent(keyName);
        }
        return Response.ok();
    }
}
