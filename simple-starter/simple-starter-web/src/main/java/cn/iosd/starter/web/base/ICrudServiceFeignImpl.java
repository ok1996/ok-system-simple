package cn.iosd.starter.web.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ICrudService接口的Feign客户端实现类
 *
 * @param <T> 实体
 * @author ok1996
 */
@Component
public class ICrudServiceFeignImpl<T, S extends CrudOperations<T>> implements ICrudService<T> {

    @Autowired
    private S feign;

    @Override
    public T apiSave(T entity) {
        return feign.apiSave(entity).checkThrow().getData();
    }

    @Override
    public T apiUpdateById(Long id, T entity) {
        return feign.apiUpdateById(id, entity).checkThrow().getData();
    }

    @Override
    public Boolean apiRemoveById(Long id) {
        return feign.apiRemoveById(id).checkThrow().getData();
    }

    @Override
    public T apiGetById(Long id) {
        return feign.apiGetById(id).checkThrow().getData();
    }

    @Override
    public List<T> apiList(T req) {
        return feign.apiList(req).checkThrow().getData();
    }

    @Override
    public Long apiCount(T req) {
        return feign.apiCount(req).checkThrow().getData();
    }
}
