package cn.iosd.base.param.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.core.JsonProcessingException;

import cn.iosd.base.param.domain.BaseParam;
import cn.iosd.base.param.vo.BaseParamCodeValueVo;
import cn.iosd.base.param.vo.BaseParamSaveReqVo;

/**
 * @author ok1996
 */
public interface IBaseParamService extends IService<BaseParam> {
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
     * 新增
     *
     * @param baseParamVo
     * @return 影响行数
     * @throws JsonProcessingException
     */
    int insertBaseParam(BaseParamSaveReqVo baseParamVo) throws JsonProcessingException;

    /**
     * 修改
     *
     * @param baseParamVo
     * @return 影响行数
     * @throws JsonProcessingException
     */
    int updateBaseParam(BaseParamSaveReqVo baseParamVo) throws JsonProcessingException;

}
