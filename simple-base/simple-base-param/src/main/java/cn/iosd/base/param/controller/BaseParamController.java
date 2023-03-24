package cn.iosd.base.param.controller;

import cn.iosd.base.param.domain.BaseParam;
import cn.iosd.base.param.service.IBaseParamService;
import cn.iosd.base.param.vo.BaseParamListReqVo;
import cn.iosd.base.param.vo.BaseParamSaveReqVo;
import cn.iosd.starter.datasource.base.BaseController;
import cn.iosd.starter.datasource.domain.PageRequest;
import cn.iosd.starter.datasource.utils.DsConvertUtil;
import cn.iosd.starter.web.domain.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ok1996
 */
@Tag(name = "基础参数配置")
@RestController
@RequestMapping("/param")
public class BaseParamController extends BaseController<BaseParam> {
    @Autowired
    private IBaseParamService baseParamService;


    @Operation(summary = "获取详细信息")
    @GetMapping(value = "/info")
    public Response<BaseParam> getInfo(@RequestParam(value = "id", required = false) Long id
            , @RequestParam(value = "paramKey", required = false) String paramKey) {
        return Response.ok(id != null ?
                baseParamService.selectBaseParamById(id) :
                baseParamService.selectBaseParamByKey(paramKey));
    }


    @Operation(summary = "新增")
    @PostMapping
    public Response<Integer> add(@RequestBody BaseParamSaveReqVo baseParamVo) throws JsonProcessingException {
        return Response.ok(baseParamService.insertBaseParam(baseParamVo));
    }

    @Operation(summary = "修改")
    @PutMapping
    public Response<Integer> edit(@RequestBody BaseParamSaveReqVo baseParamVo) throws JsonProcessingException {
        return Response.ok(baseParamService.updateBaseParam(baseParamVo));
    }

}
