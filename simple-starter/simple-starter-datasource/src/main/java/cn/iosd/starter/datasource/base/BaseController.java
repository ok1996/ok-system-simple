package cn.iosd.starter.datasource.base;

import cn.iosd.starter.datasource.domain.PageRequest;
import cn.iosd.starter.datasource.utils.BaseUtils;
import cn.iosd.starter.web.base.CrudOperations;
import cn.iosd.starter.web.domain.Response;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.v3.oas.annotations.Operation;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 单表CURD接口
 *
 * @param <T> 实体
 * @author ok1996
 */
@Component
public class BaseController<T> implements CrudOperations<T> {

    @Autowired
    private IService<T> service;

    @Override
    @Operation(summary = "Api-新增")
    public Response<T> apiSave(@RequestBody T entity) {
        BaseUtils.setValue(entity, "setCreateTime", LocalDateTime.now(), LocalDateTime.class);
        service.save(entity);
        return Response.ok(entity);
    }

    @Override
    @Operation(summary = "Api-更新-Id")
    public Response<T> apiUpdateById(@PathVariable Long id, @RequestBody T entity) {
        BaseUtils.setValue(entity, "setId", id, Long.class);
        BaseUtils.setValue(entity, "setModifyTime", LocalDateTime.now(), LocalDateTime.class);
        service.updateById(entity);
        return Response.ok(entity);
    }


    @Override
    @Operation(summary = "Api-删除")
    public Response<Boolean> apiRemoveById(@PathVariable Long id) {
        return Response.ok(service.removeById(id));
    }

    @Override
    @Operation(summary = "Api-查询-单个")
    public Response<T> apiGetById(@PathVariable Long id) {
        return Response.ok(service.getById(id));
    }

    @Override
    @Operation(summary = "Api-查询-列表")
    public Response<List<T>> apiList(@ParameterObject T req) {
        return Response.ok(service.list(Wrappers.lambdaQuery(req)));
    }

    @Override
    @Operation(summary = "Api-查询-统计")
    public Response<Long> apiCount(@ParameterObject T req) {
        return Response.ok(service.count(Wrappers.lambdaQuery(req)));
    }

    @Operation(summary = "Api-查询-分页")
    @PostMapping("/api/page")
    public Response<IPage<T>> apiPage(@RequestBody PageRequest<T> req) {
        return Response.ok(service.page(req.toPage(), req.toWrapper()));
    }

}
