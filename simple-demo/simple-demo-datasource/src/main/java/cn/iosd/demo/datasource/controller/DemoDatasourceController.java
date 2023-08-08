package cn.iosd.demo.datasource.controller;

import cn.iosd.demo.datasource.domain.DemoDatasource;
import cn.iosd.demo.datasource.service.IDemoDatasourceService;
import cn.iosd.starter.datasource.base.BaseController;
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
@Tag(name = "datasource示例模块数据库Controller")
@RestController
@RequestMapping("/simple-demo-datasource/datasource")
public class DemoDatasourceController extends BaseController<DemoDatasource> {

    @Autowired
    private IDemoDatasourceService service;

    @Operation(summary = "获取详细信息")
    @GetMapping(value = "/info")
    public Response<DemoDatasource> getInfo(Long id) {
        return Response.ok(service.selectCustomById(id));
    }

}
