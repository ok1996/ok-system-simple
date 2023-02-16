package ${package.Service};

import ${package.Entity}.${entity};
import java.util.List;

/**
 * <p>
 * ${table.comment!} 服务类
 * </p>
 *
 * @author ${author}
 */
public interface ${table.serviceName} {
    /**
     * 查询单体
     *
     * @param id 主键
     * @return
     */
    ${table.entityName} selectById(Long id);

    /**
     * 查询列表
     *
     * @param ${table.entityPath} 实体参数
     * @return 集合
     */
    List<${table.entityName}> selectList(${table.entityName} ${table.entityPath});

    /**
     * 新增
     *
     * @param ${table.entityPath} 实体
     * @return 结果
     */
    int insert(${table.entityName} ${table.entityPath});

    /**
     * 修改
     *
     * @param ${table.entityPath} 实体
     * @return 结果
     */
    int update(${table.entityName} ${table.entityPath});

    /**
     * 批量删除
     *
     * @param ids 需要删除的主键集合
     * @return 结果
     */
    int deleteByIds(Long[] ids);

    /**
     * 删除
     *
     * @param id 主键
     * @return 结果
     */
    int deleteById(Long id);
}
