package cn.iosd.starter.dict.service;

import cn.iosd.starter.dict.vo.DictItem;

import java.util.List;

/**
 * @author ok1996
 */
public interface DictService {
    /**
     * 根据参数获取对应字典列表
     *
     * @param dictionaryParams 获取对应字典列表所需的参数
     * @return 字典列表
     */
    List<DictItem> getDictItemList(String dictionaryParams);
}
