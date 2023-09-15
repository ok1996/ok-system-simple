package cn.iosd.base.dict.api.service;

import cn.iosd.starter.dict.service.DictService;
import cn.iosd.starter.dict.vo.DictItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Base服务模块自定义字典服务类-优先使用
 *
 * @author ok1996
 */
@Service
@Order(1)
public class DictBaseServiceImpl implements DictService {

    @Autowired
    private IDictInfoService dictInfoService;

    @Override
    public List<DictItem> getDictItemList(String dictionaryParams) {
        return dictInfoService.getDictItemList(dictionaryParams);
    }
}
