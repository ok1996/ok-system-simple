package cn.iosd.base.config.service.controller;

import cn.iosd.base.config.api.domain.BaseConfigInfo;
import cn.iosd.base.config.api.service.IBaseConfigService;
import cn.iosd.base.config.api.vo.BaseConfigVo;
import cn.iosd.base.config.api.vo.CodeValue;
import cn.iosd.base.config.api.vo.CodeValueListHistory;
import cn.iosd.base.config.service.entity.BaseConfigInfoEntity;
import cn.iosd.starter.datasource.base.BaseController;
import cn.iosd.starter.web.domain.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ok1996
 */
@Tag(name = "基础参数配置")
@RestController
@RequestMapping("/simple-base-config-service/configInfo")
public class BaseConfigInfoController extends BaseController<BaseConfigInfoEntity> {
    @Autowired
    private IBaseConfigService service;

    @Operation(summary = "新增")
    @PostMapping
    public Response<Integer> add(@RequestBody BaseConfigVo baseConfigVo) {
        return Response.ok(service.insert(baseConfigVo));
    }

    @Operation(summary = "修改")
    @PutMapping
    public Response<Integer> edit(@RequestBody BaseConfigVo baseConfigVo) {
        return Response.ok(service.update(baseConfigVo));
    }

    @Operation(summary = "查询")
    @GetMapping("/key/{key}")
    public Response<BaseConfigInfo> selectByKey(@PathVariable String key) {
        return Response.ok(service.selectByKey(key));
    }

    @Operation(summary = "查询参数配置")
    @GetMapping("/CodeValues/key/{key}")
    public Response<List<CodeValue<?>>> selectListByKey(@PathVariable String key) {
        return Response.ok(service.selectListByKey(key));
    }

    @Operation(summary = "查询参数配置历史")
    @GetMapping("/CodeValuesHistory/key/{key}")
    public Response<List<CodeValueListHistory>> selectListHistoryByKey(@PathVariable String key) {
        return Response.ok(service.selectListHistoryByKey(key));
    }
}
