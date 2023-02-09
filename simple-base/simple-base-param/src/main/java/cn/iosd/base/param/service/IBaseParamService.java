package cn.iosd.base.param.service;

import cn.iosd.base.param.domain.BaseParam;
import cn.iosd.base.param.vo.BaseParamCodeValueVo;
import cn.iosd.base.param.vo.BaseParamListReqVo;
import cn.iosd.base.param.vo.BaseParamSaveReqVo;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

/**
 * @author ok1996
 */
public interface IBaseParamService {
    /**
     * 查询基础参数配置
     *
     * @param id 基础参数配置主键
     * @return 基础参数配置
     */
    BaseParam selectBaseParamById(Long id);

    /**
     * 查询基础参数配置
     *
     * @param paramKey 参数主键
     * @return 基础参数配置
     */
    BaseParam selectBaseParamByKey(String paramKey);

    /**
     * 查询参数配置
     *
     * @param paramKey
     * @return
     * @throws JsonProcessingException
     */
    List<BaseParamCodeValueVo<?>> selectCodeValueVoParamByKey(String paramKey) throws JsonProcessingException;

    /**
     * 查询基础参数配置列表
     *
     * @param baseParam 基础参数配置
     * @return 基础参数配置集合
     */
    List<BaseParam> selectBaseParamList(BaseParamListReqVo baseParam);

    /**
     * 新增
     *
     * @param baseParamVo
     * @return 主键id
     * @throws JsonProcessingException
     */
    Long insertBaseParam(BaseParamSaveReqVo baseParamVo) throws JsonProcessingException;

    /**
     * 修改
     *
     * @param baseParamVo
     * @return 影响行数
     * @throws JsonProcessingException
     */
    int updateBaseParam(BaseParamSaveReqVo baseParamVo) throws JsonProcessingException;

    /**
     * 批量删除基础参数配置
     *
     * @param ids 需要删除的基础参数配置主键集合
     * @return 影响行数
     */
    int deleteBaseParamByIds(Long[] ids);

    /**
     * 删除基础参数配置信息
     *
     * @param id 基础参数配置主键
     * @return 影响行数
     */
    int deleteBaseParamById(Long id);
}
