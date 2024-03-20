package cn.iosd.base.dict.service.service;

import cn.iosd.base.dict.api.domain.DictInfo;
import cn.iosd.base.dict.api.service.IDictInfoService;
import cn.iosd.base.dict.api.vo.DictGroupVo;
import cn.iosd.base.dict.service.entity.DictInfoEntity;
import cn.iosd.base.dict.service.mapper.DictInfoMapper;
import cn.iosd.starter.datasource.base.BaseServiceImpl;
import cn.iosd.starter.dict.vo.DictItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 字典数据 服务实现类-Mybatis
 * </p>
 *
 * @author ok1996
 */
@Service
@Primary
public class DictInfoServiceImpl extends BaseServiceImpl<DictInfoMapper, DictInfoEntity, DictInfo> implements IDictInfoService {

    @Override
    public List<DictItem> getDictItemList(String dictionaryParams) {
        DictInfoEntity queryDb = new DictInfoEntity();
        queryDb.setDictGroupParam(dictionaryParams);
        queryDb.setDictStatus(0);
        List<DictInfoEntity> dictInfoList = baseMapper.selectList(Wrappers.lambdaQuery(queryDb));
        return dictInfoList.stream()
                .map(v -> new DictItem(v.getDictValue(), v.getDictDesc()))
                .collect(Collectors.toList());
    }

    @Override
    public List<DictGroupVo> getUniqueDictGroups() {
        return baseMapper.getUniqueDictGroups();
    }
}
