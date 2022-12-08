package cn.iosd.demo.boot3.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ok96
 */
@RestController
@RequestMapping("hello")
public class HelloController {
    @GetMapping(value = "/world")
    public String world() {
        return "hello world";
    }
}
