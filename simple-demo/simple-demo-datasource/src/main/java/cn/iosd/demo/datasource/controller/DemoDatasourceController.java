package cn.iosd.demo.datasource.controller;

import cn.iosd.demo.datasource.domain.DemoDatasource;
import cn.iosd.demo.datasource.service.IDemoDatasourceService;
import cn.iosd.starter.datasource.domain.PageRequest;
import cn.iosd.starter.datasource.domain.PageResponse;
import cn.iosd.starter.web.domain.Response;
import com.github.pagehelper.PageHelper;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ok1996
 * @date 2022-12-14
 */
@Tag(name = "datasource示例模块数据库Controller")
@RestController
@RequestMapping("/datasource")
public class DemoDatasourceController {
    @Autowired
    private IDemoDatasourceService demoDatasourceService;

    @Operation(summary = "查询列表")
    @GetMapping("/list")
    public Response<List<DemoDatasource>> list(@ParameterObject DemoDatasource demoDatasource) {
        return Response.ok(demoDatasourceService.selectDemoDatasourceList(demoDatasource));
    }

    @Operation(summary = "查询分页")
    @PostMapping("/page")
    public Response<PageResponse<DemoDatasource>> page(@RequestBody PageRequest<DemoDatasource> req) {
        PageHelper.startPage(req.getPageNum(), req.getPageSize(), req.getOrderBy());
        return Response.ok(
                PageResponse.getPage(
                        demoDatasourceService.selectDemoDatasourceList(req.getData())
                ));
    }

    @Operation(summary = "获取详细信息")
    @GetMapping(value = "/{id}")
    public Response<DemoDatasource> getInfo(@PathVariable("id") Long id) {
        return Response.ok(demoDatasourceService.selectDemoDatasourceById(id));
    }

    @Operation(summary = "新增")
    @PostMapping
    public Response<Integer> add(@RequestBody DemoDatasource demoDatasource) {
        return Response.ok(demoDatasourceService.insertDemoDatasource(demoDatasource));
    }

    @Operation(summary = "修改")
    @PutMapping
    public Response<Integer> edit(@RequestBody DemoDatasource demoDatasource) {
        return Response.ok(demoDatasourceService.updateDemoDatasource(demoDatasource));
    }

    @Operation(summary = "删除")
    @DeleteMapping("/{ids}")
    public Response<Integer> remove(@PathVariable Long[] ids) {
        return Response.ok(demoDatasourceService.deleteDemoDatasourceByIds(ids));
    }
}
