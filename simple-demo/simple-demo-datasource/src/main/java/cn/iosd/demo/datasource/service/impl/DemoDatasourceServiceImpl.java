package cn.iosd.demo.datasource.service.impl;

import cn.iosd.demo.datasource.domain.DemoDatasource;
import cn.iosd.demo.datasource.mapper.DemoDatasourceMapper;
import cn.iosd.demo.datasource.service.IDemoDatasourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * Service业务层处理
 *
 * @author ok1996
 * @date 2022-12-14
 */
@Service
public class DemoDatasourceServiceImpl extends ServiceImpl<DemoDatasourceMapper, DemoDatasource> implements IDemoDatasourceService {

    @Override
    public DemoDatasource selectCustomById(Long id) {
        return baseMapper.selectCustomById(id);
    }
}
