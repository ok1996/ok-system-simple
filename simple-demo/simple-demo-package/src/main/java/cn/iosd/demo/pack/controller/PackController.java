package cn.iosd.demo.pack.controller;

import cn.iosd.starter.web.domain.Response;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ok1996
 */
@RestController
@RequestMapping("/simple-demo-package")
public class PackController {

    @Operation(summary = "测试")
    @GetMapping("test")
    public Response<?> test() {
        return Response.ok();
    }

}
