package cn.iosd.starter.datasource.base;

import cn.iosd.starter.datasource.domain.PageRequest;
import cn.iosd.starter.datasource.utils.DsConvertUtil;
import cn.iosd.starter.web.domain.Response;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.v3.oas.annotations.Operation;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ok1996
 */
public class BaseController<T> {

    @Autowired
    private IService<T> service;

    @Operation(summary = "Api-新增")
    @PostMapping("/api")
    public Response<Boolean> apiSave(@RequestBody T entity) {
        setCreateTimeMethod(entity);
        return Response.ok(service.save(entity));
    }

    @Operation(summary = "Api-更新-Id")
    @PutMapping("/api")
    public Response<Boolean> apiUpdateById(@RequestBody T entity) {
        setModifyTimeMethod(entity);
        return Response.ok(service.updateById(entity));
    }

    @Operation(summary = "Api-删除")
    @DeleteMapping("/api/{id}")
    public Response<Boolean> apiRemoveById(@PathVariable Long id) {
        return Response.ok(service.removeById(id));
    }

    @Operation(summary = "Api-查询-单个")
    @GetMapping("/api/{id}")
    public Response<T> apiGetById(@PathVariable Long id) {
        return Response.ok(service.getById(id));
    }

    @Operation(summary = "Api-查询-列表")
    @GetMapping("/api/list")
    public Response<List<T>> apiList(@ParameterObject T req) {
        return Response.ok(service.list(DsConvertUtil.queryWrapperEqual(req)));
    }

    @Operation(summary = "Api-查询-分页")
    @PostMapping("/api/page")
    public Response<IPage<T>> apiPage(@RequestBody PageRequest<T> req) {
        return Response.ok(service.page(DsConvertUtil.page(req), DsConvertUtil.queryWrapperEqual(req.getData())));
    }


    private void setCreateTimeMethod(T entity) {
        try {
            Method setCreateTimeMethod = entity.getClass().getMethod("setCreateTime", LocalDateTime.class);
            setCreateTimeMethod.invoke(entity, LocalDateTime.now());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            // T实体不存在setCreateTime方法，不做处理
        }
    }

    private void setModifyTimeMethod(T entity) {
        try {
            Method setCreateTimeMethod = entity.getClass().getMethod("setModifyTime", LocalDateTime.class);
            setCreateTimeMethod.invoke(entity, LocalDateTime.now());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            // T实体不存在setModifyTime方法，不做处理
        }
    }

}
