package cn.iosd.base.dict.service.controller;

import cn.iosd.base.dict.api.service.IDictInfoService;
import cn.iosd.base.dict.api.vo.DictGroupVo;
import cn.iosd.base.dict.service.entity.DictInfoEntity;
import cn.iosd.starter.datasource.base.BaseController;
import cn.iosd.starter.dict.vo.DictItem;
import cn.iosd.starter.web.domain.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 字典数据 前端控制器
 * </p>
 *
 * @author ok1996
 */
@Tag(name = "字典数据")
@RestController
@RequestMapping("/simple-base-dict-service/DictInfo")
public class DictInfoController extends BaseController<DictInfoEntity>{

    @Autowired
    private IDictInfoService service;

    @Operation(summary = "字典翻译-远程调用接口实现类字典")
    @GetMapping("/custom/{param}")
    public Response<List<DictItem>> customDict(@PathVariable String param) {
        return Response.ok(service.getDictItemList(param));
    }

    @Operation(summary = "提取字典分组")
    @GetMapping("/uniqueDictGroups")
    public Response<List<DictGroupVo>> uniqueDictGroups() {
        return Response.ok(service.getUniqueDictGroups());
    }

}
