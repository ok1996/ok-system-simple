package cn.iosd.base.param.controller;

import cn.iosd.base.param.api.domain.BaseParam;
import cn.iosd.base.param.api.service.IBaseParamService;
import cn.iosd.base.param.api.vo.BaseParamVo;
import cn.iosd.base.param.api.vo.CodeValue;
import cn.iosd.base.param.entity.BaseParamEntity;
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
@RequestMapping("/simple-base-param-service/param")
public class BaseParamController extends BaseController<BaseParamEntity> {
    @Autowired
    private IBaseParamService baseParamService;

    @Operation(summary = "新增")
    @PostMapping
    public Response<Integer> add(@RequestBody BaseParamVo baseParamVo) {
        return Response.ok(baseParamService.insertBaseParam(baseParamVo));
    }

    @Operation(summary = "修改")
    @PutMapping
    public Response<Integer> edit(@RequestBody BaseParamVo baseParamVo) {
        return Response.ok(baseParamService.updateBaseParam(baseParamVo));
    }

    @Operation(summary = "查询")
    @GetMapping("/BaseParam/key/{paramKey}")
    public Response<BaseParam> selectBaseParamByKey(@PathVariable String paramKey) {
        return Response.ok(baseParamService.selectBaseParamByKey(paramKey));
    }

    @Operation(summary = "查询参数配置")
    @GetMapping("/CodeValues/key/{paramKey}")
    public Response<List<CodeValue<?>>> selectCodeValueVoParamByKey(@PathVariable String paramKey) {
        return Response.ok(baseParamService.selectCodeValueVoParamByKey(paramKey));
    }

}
