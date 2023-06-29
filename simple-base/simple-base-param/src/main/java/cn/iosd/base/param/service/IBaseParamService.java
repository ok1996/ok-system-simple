package cn.iosd.base.param.service;

import cn.iosd.base.param.domain.BaseParam;
import cn.iosd.base.param.vo.CodeValue;
import cn.iosd.base.param.vo.BaseParamVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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
     */
    List<CodeValue<?>> selectCodeValueVoParamByKey(String paramKey);

    /**
     * 新增
     *
     * @param baseParamVo
     * @return 影响行数
     */
    int insertBaseParam(BaseParamVo baseParamVo);

    /**
     * 修改
     *
     * @param baseParamVo
     * @return 影响行数
     */
    int updateBaseParam(BaseParamVo baseParamVo);

}
