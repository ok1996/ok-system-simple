package cn.iosd.demo.base.param.service.controller;

import cn.iosd.demo.base.param.service.service.TestService;
import cn.iosd.demo.base.param.service.vo.ClassmateVo;
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
@Tag(name = "测试模块")
@RestController
@RequestMapping("/demo-base-param-service/test")
public class TestController {

    @Autowired
    private TestService service;

    @Operation(summary = "获取同班同学列表")
    @GetMapping(value = "/classmateList")
    public Response<ClassmateVo> classmateList() {
        return Response.ok(service.classmateList());
    }
}
