package cn.iosd.demo.redisson.controller;

import cn.iosd.starter.redisson.annotation.DistributedRateLimiter;
import cn.iosd.starter.web.domain.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.redisson.api.RateType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ok1996
 */
@RestController
@Tag(name = "限流-注解模式-测试请求")
@RequestMapping("/simple-demo-redisson/rateLimiter")
public class RateLimiterController {

    @Operation(summary = "限流器-单客户端")
    @GetMapping("perClient")
    @DistributedRateLimiter(type = RateType.PER_CLIENT)
    public Response<?> distributedClientRateLimiter() {
        return Response.ok();
    }

    @Operation(summary = "限流器-单客户端")
    @GetMapping("overall")
    @DistributedRateLimiter
    public Response<?> overall() {
        return Response.ok();
    }
}
