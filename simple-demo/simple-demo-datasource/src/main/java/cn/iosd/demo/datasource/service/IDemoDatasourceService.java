package cn.iosd.demo.datasource.service;

import cn.iosd.demo.datasource.domain.DemoDatasource;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * Service接口
 *
 * @author ok1996
 * @date 2022-12-14
 */
public interface IDemoDatasourceService extends IService<DemoDatasource> {

    /**
     * 自定义查询方法
     * @param id 主键
     * @return 返回数据实体
     */
    DemoDatasource selectCustomById(Long id);
}
