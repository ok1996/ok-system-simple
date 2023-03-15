package cn.iosd.demo.encode.controller;

import cn.iosd.demo.encode.service.TestService;
import cn.iosd.demo.encode.vo.PersonVo;
import cn.iosd.starter.web.domain.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ok1996
 */
@Tag(name = "脱敏测试模块")
@RestController
@RequestMapping("test")
public class TestController {
    @Autowired
    private TestService service;

    @Operation(summary = "获取列表")
    @GetMapping(value = "/personList")
    public Response<PersonVo> personList() {
        return Response.ok(service.getPerson());
    }
}
