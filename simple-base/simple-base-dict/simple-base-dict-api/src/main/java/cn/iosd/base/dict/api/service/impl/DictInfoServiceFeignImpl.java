package cn.iosd.base.dict.api.service.impl;

import cn.iosd.base.dict.api.domain.DictInfo;
import cn.iosd.base.dict.api.feign.DictInfoFeign;
import cn.iosd.base.dict.api.service.IDictInfoService;
import cn.iosd.base.dict.api.vo.DictGroupVo;
import cn.iosd.starter.dict.vo.DictItem;
import cn.iosd.starter.web.base.ICrudServiceFeignImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 字典数据 服务实现类-Feign
 * </p>
 *
 * @author ok1996
 */
@Service
public class DictInfoServiceFeignImpl extends ICrudServiceFeignImpl<DictInfo, DictInfoFeign> implements IDictInfoService {

    @Autowired
    private DictInfoFeign dictinfoFeign;

    @Override
    public List<DictItem> getDictItemList(String dictionaryParams) {
        return dictinfoFeign.getDictItemList(dictionaryParams).checkThrow().getData();
    }

    @Override
    public List<DictGroupVo> getUniqueDictGroups() {
        return dictinfoFeign.uniqueDictGroups().checkThrow().getData();
    }
}
