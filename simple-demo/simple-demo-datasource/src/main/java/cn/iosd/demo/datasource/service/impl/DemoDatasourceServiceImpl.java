package cn.iosd.demo.datasource.service.impl;

import cn.iosd.demo.datasource.domain.DemoDatasource;
import cn.iosd.demo.datasource.mapper.DemoDatasourceMapper;
import cn.iosd.demo.datasource.service.IDemoDatasourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service业务层处理
 *
 * @author ok1996
 * @date 2022-12-14
 */
@Service
public class DemoDatasourceServiceImpl implements IDemoDatasourceService {
    @Autowired
    private DemoDatasourceMapper demoDatasourceMapper;

    @Override
    public DemoDatasource selectDemoDatasourceById(Long id) {
        return demoDatasourceMapper.selectDemoDatasourceById(id);
    }

    @Override
    public List<DemoDatasource> selectDemoDatasourceList(DemoDatasource demoDatasource) {
        return demoDatasourceMapper.selectDemoDatasourceList(demoDatasource);
    }

    @Override
    public int insertDemoDatasource(DemoDatasource demoDatasource) {
        return demoDatasourceMapper.insertDemoDatasource(demoDatasource);
    }

    @Override
    public int updateDemoDatasource(DemoDatasource demoDatasource) {
        return demoDatasourceMapper.updateDemoDatasource(demoDatasource);
    }

    @Override
    public int deleteDemoDatasourceByIds(Long[] ids) {
        return demoDatasourceMapper.deleteDemoDatasourceByIds(ids);
    }

    @Override
    public int deleteDemoDatasourceById(Long id) {
        return demoDatasourceMapper.deleteDemoDatasourceById(id);
    }
}
