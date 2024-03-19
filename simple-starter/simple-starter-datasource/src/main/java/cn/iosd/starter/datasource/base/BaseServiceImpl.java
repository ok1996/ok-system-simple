package cn.iosd.starter.datasource.base;

import cn.iosd.starter.datasource.utils.BaseUtils;
import cn.iosd.starter.web.base.ICrudService;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 单表CURD服务DB实现类
 *
 * @param <T> 实体
 * @author ok1996
 */
@Primary
@Component
public class BaseServiceImpl<M extends BaseMapper<E>, E extends T, T> extends ServiceImpl<M, E> implements ICrudService<T> {

    @Autowired
    private M mapper;

    @Override
    public T apiSave(T entity) {
        BaseUtils.setValue(entity, "setCreateTime", LocalDateTime.now(), LocalDateTime.class);
        mapper.insert((E) entity);
        return entity;
    }

    @Override
    public T apiUpdateById(Long id, T entity) {
        BaseUtils.setValue(entity, "setId", id, Long.class);
        BaseUtils.setValue(entity, "setModifyTime", LocalDateTime.now(), LocalDateTime.class);
        mapper.updateById((E) entity);
        return null;
    }

    @Override
    public Boolean apiRemoveById(Long id) {
        return mapper.deleteById(id) > 0;
    }

    @Override
    public T apiGetById(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public List<T> apiList(T req) {
        return (List<T>) mapper.selectList(Wrappers.lambdaQuery((E) req));
    }

    @Override
    public Long apiCount(T req) {
        return mapper.selectCount(Wrappers.lambdaQuery((E) req));
    }
}
