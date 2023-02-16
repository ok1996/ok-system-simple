package ${package.Controller};

import ${package.Entity}.${table.entityName};
import ${package.Service}.${table.serviceName};
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.iosd.starter.datasource.domain.PageRequest;
import cn.iosd.starter.datasource.domain.PageResponse;
import cn.iosd.starter.web.domain.Response;
import com.github.pagehelper.PageHelper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 */
@RestController
@RequestMapping("<#if package.ModuleName?? && package.ModuleName != "">/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
public class ${table.controllerName} {
    @Autowired
    private ${table.serviceName} service;

    @Operation(summary = "查询列表")
    @PostMapping("/list")
    public Response<List<${table.entityName}>> list(@RequestBody ${table.entityName} ${table.entityPath}) {
        return Response.ok(service.selectList(${table.entityPath}));
    }

    @Operation(summary = "查询分页")
    @PostMapping("/page")
    public Response<PageResponse<${table.entityName}>> page(@RequestBody PageRequest<${table.entityName}> req) {
        PageHelper.startPage(req.getPageNum(), req.getPageSize(), req.getOrderBy());
        return Response.ok(
                PageResponse.getPage(
                        service.selectList(req.getData())
                ));
    }

    @Operation(summary = "获取详细信息")
    @GetMapping(value = "/{id}")
    public Response<${table.entityName}> getInfo(@PathVariable("id") Long id) {
        return Response.ok(service.selectById(id));
    }

    @Operation(summary = "新增")
    @PostMapping
    public Response<Integer> add(@RequestBody ${table.entityName} ${table.entityPath}) {
        return Response.ok(service.insert(${table.entityPath}));
    }

    @Operation(summary = "修改")
    @PutMapping
    public Response<Integer> edit(@RequestBody ${table.entityName} ${table.entityPath}) {
        return Response.ok(service.update(${table.entityPath}));
    }

    @Operation(summary = "删除")
    @DeleteMapping("/{ids}")
    public Response<Integer> remove(@PathVariable Long[] ids) {
        return Response.ok(service.deleteByIds(ids));
    }
}
