package cn.iosd.demo.redisson.controller;

import cn.iosd.starter.redisson.annotation.DistributedIdempotent;
import cn.iosd.starter.web.domain.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ok1996
 */
@Slf4j
@RestController
@Tag(name = "分布式幂等-注解模式-测试请求")
@RequestMapping("/annotationIdempotent")
public class AnnotationIdempotentController {
    /**
     * 商品库存
     */
    public static volatile Integer TOTAL = 10000;

    @Operation(summary = "库存自减")
    @GetMapping("decrement")
    @DistributedIdempotent
    public Response<?> decrement() {
        if (TOTAL > 0) {
            TOTAL--;
        }
        log.info("===/annotationIdempotent/decrement注解模式=== 减完库存后,当前库存===" + TOTAL);
        return Response.ok();
    }

    @Operation(summary = "库存自减-请求参数变量-在业务执行结束后解锁")
    @GetMapping("decrementReqPara")
    @DistributedIdempotent(value = "#keyName", message = "请求重复！", expireTime = 5)
    public Response<?> decrementReqPara(String keyName) {
        if (TOTAL > 0) {
            TOTAL--;
        }
        log.info("===/annotationIdempotent/decrementReqPara注解模式=== 减完库存后,当前库存===" + TOTAL);
        return Response.ok();
    }

    @Operation(summary = "库存自减-请求参数变量-等待expireTime自动过期后自动解锁")
    @GetMapping("decrementReqPara/executionFinishedUnlock")
    @DistributedIdempotent(value = "#keyName", message = "请求重复！", expireTime = 5, executionFinishedUnlock = false)
    public Response<?> decrementReqParaExecutionFinishedUnlock(String keyName) {
        if (TOTAL > 0) {
            TOTAL--;
        }
        log.info("===/annotationIdempotent/decrementReqPara/executionFinishedUnlock注解模式=== 减完库存后,当前库存===" + TOTAL);
        return Response.ok();
    }

    @Operation(summary = "库存自减-异常测试")
    @GetMapping("decrementException")
    @DistributedIdempotent(value = "'demo:singIn4:' + #keyName", message = "请求重复！")
    public void decrementException(String keyName) throws Exception {
        if (TOTAL > 0) {
            TOTAL--;
        }
        log.info("===/annotationIdempotent/decrementException注解模式=== 减完库存后,当前库存===" + TOTAL);
        throw new Exception("异常测试");
    }
}
