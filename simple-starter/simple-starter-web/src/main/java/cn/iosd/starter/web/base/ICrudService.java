package cn.iosd.starter.web.base;

import java.util.List;

/**
 * 定义了对 T 进行CRUD操作的服务层方法
 *
 * @author ok1996
 */
public interface ICrudService<T> {
    /**
     * Api-新增
     *
     * @param entity 数据实体
     * @return 返回结果
     */
    T apiSave(T entity);

    /**
     * Api-更新-Id
     *
     * @param id     主键Id
     * @param entity 数据实体
     * @return 返回结果
     */
    T apiUpdateById(Long id, T entity);

    /**
     * Api-删除
     *
     * @param id 主键Id
     * @return 返回结果
     */
    Boolean apiRemoveById(Long id);

    /**
     * Api-查询-单个
     *
     * @param id 主键Id
     * @return 返回结果
     */
    T apiGetById(Long id);

    /**
     * Api-查询-列表
     *
     * @param req 查询参数
     * @return 返回结果列表
     */
    List<T> apiList(T req);

    /**
     * Api-查询-统计
     *
     * @param req 查询参数
     * @return 返回结果数量
     */
    Long apiCount(T req);
}