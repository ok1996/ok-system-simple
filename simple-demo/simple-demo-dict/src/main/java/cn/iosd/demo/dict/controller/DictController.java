package cn.iosd.demo.dict.controller;

import cn.iosd.demo.dict.service.DictService;
import cn.iosd.starter.dict.vo.DictItem;
import cn.iosd.starter.web.domain.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ok1996
 */
@Tag(name = "提供字典数据接口")
@RestController
@RequestMapping("dict")
public class DictController {
    @Autowired
    private DictService service;

    @Operation(summary = "字典翻译-自定义远程调用接口实现类字典")
    @GetMapping("/custom/{param}")
    public Response<List<DictItem>> customDict(@PathVariable String param) {
        return Response.ok(service.remoteDict(param));
    }

    @Operation(summary = "字典翻译-默认实现类的远程调用接口")
    @GetMapping("/remote/{param}")
    public ResponseEntity<List<DictItem>> remoteDict(@PathVariable String param) {
        return new ResponseEntity<List<DictItem>>(service.remoteDict(param), HttpStatus.OK);
    }
}
