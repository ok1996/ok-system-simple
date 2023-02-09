package cn.iosd.base.param.mapper;

import cn.iosd.base.param.domain.BaseParam;
import cn.iosd.base.param.vo.BaseParamListReqVo;

import java.util.List;

/**
 * @author ok1996
 */
public interface BaseParamMapper {
    /**
     * 查询基础参数配置
     *
     * @param id 基础参数配置主键
     * @return 基础参数配置
     */
    BaseParam selectBaseParamById(Long id);

    /**
     * 查询基础参数配置列表
     *
     * @param baseParam 基础参数配置
     * @return 基础参数配置集合
     */
    List<BaseParam> selectBaseParamList(BaseParamListReqVo baseParam);

    /**
     * 新增基础参数配置
     *
     * @param baseParam 基础参数配置
     * @return 结果
     */
    int insertBaseParam(BaseParam baseParam);

    /**
     * 修改基础参数配置
     *
     * @param baseParam 基础参数配置
     * @return 结果
     */
    int updateBaseParam(BaseParam baseParam);

    /**
     * 删除基础参数配置
     *
     * @param id 基础参数配置主键
     * @return 结果
     */
    int deleteBaseParamById(Long id);

    /**
     * 批量删除基础参数配置
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteBaseParamByIds(Long[] ids);

    /**
     * 查询基础参数配置
     *
     * @param paramKey 参数主键
     * @return 基础参数配置
     */
    BaseParam selectBaseParamByKey(String paramKey);
}
