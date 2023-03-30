package cn.iosd.demo.dict.controller;

import cn.iosd.demo.dict.service.DictService;
import cn.iosd.demo.dict.vo.PersonRemoteVo;
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
@Tag(name = "字典测试模块-自定义远程调用接口实现类字典及默认远程调用接口实现类字典")
@RestController
@RequestMapping("dictCustom")
public class DictCustomController {
    @Autowired
    private DictService service;

    @Operation(summary = "测试单体")
    @GetMapping(value = "/personRemoteVo")
    public Response<PersonRemoteVo> getPersonRemoteVo() {
        return Response.ok(service.getPersonRemoteVo());
    }

}
