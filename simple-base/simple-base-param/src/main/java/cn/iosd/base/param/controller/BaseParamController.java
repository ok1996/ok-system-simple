package cn.iosd.base.param.controller;

import cn.iosd.base.param.domain.BaseParam;
import cn.iosd.base.param.service.IBaseParamService;
import cn.iosd.base.param.vo.BaseParamSaveReqVo;
import cn.iosd.starter.datasource.domain.PageRequest;
import cn.iosd.starter.datasource.utils.DsConvertUtil;
import cn.iosd.starter.web.domain.Response;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/param")
public class BaseParamController {
    @Autowired
    private IBaseParamService baseParamService;

    @Autowired
    private IService<BaseParam> service;

    @Operation(summary = "获取详细信息")
    @GetMapping(value = "/info")
    public Response<BaseParam> getInfo(Long id, String paramKey) {
        return Response.ok(id != null ?
                baseParamService.selectBaseParamById(id) :
                baseParamService.selectBaseParamByKey(paramKey));
    }

    @Operation(summary = "新增")
    @PostMapping
    public Response<Integer> add(@RequestBody BaseParamSaveReqVo baseParamVo) {
        return Response.ok(baseParamService.insertBaseParam(baseParamVo));
    }

    @Operation(summary = "修改")
    @PutMapping
    public Response<Integer> edit(@RequestBody BaseParamSaveReqVo baseParamVo) {
        return Response.ok(baseParamService.updateBaseParam(baseParamVo));
    }

    @Operation(summary = "Api-删除")
    @DeleteMapping("/api/{id}")
    public Response<Boolean> apiRemoveById(@PathVariable Long id) {
        return Response.ok(service.removeById(id));
    }

    @Operation(summary = "Api-查询-单个")
    @GetMapping("/api/{id}")
    public Response<BaseParam> apiGetById(@PathVariable Long id) {
        return Response.ok(service.getById(id));
    }

    @Operation(summary = "Api-查询-列表")
    @GetMapping("/api/list")
    public Response<List<BaseParam>> apiList(@ParameterObject BaseParam req) {
        return Response.ok(service.list(Wrappers.lambdaQuery(req)));
    }

    @Operation(summary = "Api-查询-分页")
    @PostMapping("/api/page")
    public Response<IPage<BaseParam>> apiPage(@RequestBody PageRequest<BaseParam> req) {
        return Response.ok(service.page(DsConvertUtil.page(req), Wrappers.lambdaQuery(req.getData())));
    }

}
