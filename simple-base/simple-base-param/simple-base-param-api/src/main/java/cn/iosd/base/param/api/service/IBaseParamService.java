package cn.iosd.base.param.api.service;

import cn.iosd.base.param.api.domain.BaseParam;
import cn.iosd.base.param.api.vo.BaseParamVo;
import cn.iosd.base.param.api.vo.CodeValue;

import java.util.List;

/**
 * @author ok1996
 */
public interface IBaseParamService{

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
     * @param paramKey 参数主键
     * @return 参数配置值
     */
    List<CodeValue<?>> selectCodeValueVoParamByKey(String paramKey);

    /**
     * 新增
     *
     * @param baseParamVo 参数配置实体
     * @return 影响行数
     */
    int insertBaseParam(BaseParamVo baseParamVo);

    /**
     * 修改
     *
     * @param baseParamVo 参数配置实体
     * @return 影响行数
     */
    int updateBaseParam(BaseParamVo baseParamVo);

}
