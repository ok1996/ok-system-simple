package cn.iosd.demo.datasource.mapper;

import cn.iosd.demo.datasource.domain.DemoDatasource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * datasource示例模块数据库Mapper接口
 *
 * @author ok1996
 * @date 2022-12-14
 */
public interface DemoDatasourceMapper extends BaseMapper<DemoDatasource> {

    DemoDatasource selectCustomById(Long id);
}
