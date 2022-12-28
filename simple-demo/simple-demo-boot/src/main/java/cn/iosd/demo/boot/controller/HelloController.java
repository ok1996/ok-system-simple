package cn.iosd.demo.boot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ok1996
 */
@RestController
@RequestMapping("hello")
@Tag(name = "你好")
public class HelloController {

    @Operation(summary = "世界")
    @GetMapping(value = "/world")
    public String world() {
        return "hello world";
    }

}
