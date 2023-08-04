package cn.iosd.starter.web.base;

import cn.iosd.starter.web.domain.Response;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

/**
 * @author ok1996
 */
public interface CrudOperations<T> {
    /**
     * Api-新增
     *
     * @param entity 数据实体
     * @return 返回结果
     */
    @PostMapping("/api")
    Response<Boolean> apiSave(@RequestBody T entity);

    /**
     * Api-更新-Id
     *
     * @param id     主键Id
     * @param entity 数据实体
     * @return 返回结果
     */
    @PutMapping("/api/{id}")
    Response<Boolean> apiUpdateById(@PathVariable Long id, @RequestBody T entity);

    /**
     * Api-删除
     *
     * @param id 主键Id
     * @return 返回结果
     */
    @DeleteMapping("/api/{id}")
    Response<Boolean> apiRemoveById(@PathVariable Long id);

    /**
     * Api-查询-单个
     *
     * @param id 主键Id
     * @return 返回结果
     */
    @GetMapping("/api/{id}")
    Response<T> apiGetById(@PathVariable Long id);

    /**
     * Api-查询-列表
     *
     * @param req 查询参数
     * @return 返回结果列表
     */
    @GetMapping("/api/list")
    Response<List<T>> apiList(@ParameterObject T req);

}