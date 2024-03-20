package cn.iosd.base.dict.api.service;


import cn.iosd.base.dict.api.domain.DictInfo;
import cn.iosd.base.dict.api.vo.DictGroupVo;
import cn.iosd.starter.dict.vo.DictItem;
import cn.iosd.starter.web.base.ICrudService;

import java.util.List;

/**
 * <p>
 * 字典数据 服务类 -继承：字典配置服务
 * </p>
 *
 * @author ok1996
 */
public interface IDictInfoService extends ICrudService<DictInfo> {
    /**
     * 获取指定类型的字典项列表
     *
     * @param dictionaryParams 接口实现类所需的参数类型
     * @return 参数类型对应字典列表
     */
    List<DictItem> getDictItemList(String dictionaryParams);

    /**
     * 提取已存在数据的字典分组
     *
     * @return 字典分组数据
     */
    List<DictGroupVo> getUniqueDictGroups();
}
