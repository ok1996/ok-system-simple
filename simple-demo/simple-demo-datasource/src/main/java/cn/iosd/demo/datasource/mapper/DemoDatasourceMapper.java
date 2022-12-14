package cn.iosd.demo.datasource.mapper;

import cn.iosd.demo.datasource.domain.DemoDatasource;

import java.util.List;

/**
 * datasource示例模块数据库Mapper接口
 *
 * @author ok1996
 * @date 2022-12-14
 */
public interface DemoDatasourceMapper {
    /**
     * 查询datasource示例模块数据库
     *
     * @param id datasource示例模块数据库主键
     * @return datasource示例模块数据库
     */
    public DemoDatasource selectDemoDatasourceById(Long id);

    /**
     * 查询datasource示例模块数据库列表
     *
     * @param demoDatasource datasource示例模块数据库
     * @return datasource示例模块数据库集合
     */
    public List<DemoDatasource> selectDemoDatasourceList(DemoDatasource demoDatasource);

    /**
     * 新增datasource示例模块数据库
     *
     * @param demoDatasource datasource示例模块数据库
     * @return 结果
     */
    public int insertDemoDatasource(DemoDatasource demoDatasource);

    /**
     * 修改datasource示例模块数据库
     *
     * @param demoDatasource datasource示例模块数据库
     * @return 结果
     */
    public int updateDemoDatasource(DemoDatasource demoDatasource);

    /**
     * 删除datasource示例模块数据库
     *
     * @param id datasource示例模块数据库主键
     * @return 结果
     */
    public int deleteDemoDatasourceById(Long id);

    /**
     * 批量删除datasource示例模块数据库
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDemoDatasourceByIds(Long[] ids);
}
