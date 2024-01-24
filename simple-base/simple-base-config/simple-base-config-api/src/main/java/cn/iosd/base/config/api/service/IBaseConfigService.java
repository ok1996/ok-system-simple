package cn.iosd.base.config.api.service;

import cn.iosd.base.config.api.domain.BaseConfigInfo;
import cn.iosd.base.config.api.vo.BaseConfigVo;
import cn.iosd.base.config.api.vo.CodeValue;
import cn.iosd.base.config.api.vo.CodeValueListHistory;

import java.util.List;

/**
 * @author ok1996
 */
public interface IBaseConfigService {

    /**
     * 查询基础参数配置
     *
     * @param key 参数主键
     * @return 基础参数配置
     */
    BaseConfigInfo selectByKey(String key);

    /**
     * 查询参数配置
     *
     * @param key 参数主键
     * @return 参数配置值
     */
    List<CodeValue<?>> selectValueListByKey(String key);

    /**
     * 新增
     *
     * @param baseConfigVo 参数配置实体
     * @return 影响行数
     */
    int insert(BaseConfigVo baseConfigVo);

    /**
     * 修改
     *
     * @param baseConfigVo 参数配置实体
     * @return 影响行数
     */
    int update(BaseConfigVo baseConfigVo);

    /**
     * 查询参数配置历史
     *
     * @param key 参数主键
     * @return 参数配置历史
     */
    List<CodeValueListHistory> selectValueListHistoryByKey(String key);
}
