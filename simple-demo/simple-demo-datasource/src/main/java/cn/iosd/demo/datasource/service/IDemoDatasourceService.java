package cn.iosd.demo.datasource.service;

import cn.iosd.demo.datasource.domain.DemoDatasource;

import java.util.List;

/**
 * Service接口
 *
 * @author ok1996
 * @date 2022-12-14
 */
public interface IDemoDatasourceService {
    /**
     * 查询单体
     *
     * @param id 主键
     * @return
     */
    DemoDatasource selectDemoDatasourceById(Long id);

    /**
     * 查询列表
     *
     * @param demoDatasource 实体参数
     * @return 集合
     */
    List<DemoDatasource> selectDemoDatasourceList(DemoDatasource demoDatasource);

    /**
     * 新增
     *
     * @param demoDatasource 实体
     * @return 结果
     */
    int insertDemoDatasource(DemoDatasource demoDatasource);

    /**
     * 修改
     *
     * @param demoDatasource 实体
     * @return 结果
     */
    int updateDemoDatasource(DemoDatasource demoDatasource);

    /**
     * 批量删除
     *
     * @param ids 需要删除的主键集合
     * @return 结果
     */
    int deleteDemoDatasourceByIds(Long[] ids);

    /**
     * 删除
     *
     * @param id 主键
     * @return 结果
     */
    int deleteDemoDatasourceById(Long id);
}
