package cn.iosd.starter.dict.service;

import cn.iosd.starter.dict.vo.DictItem;

import java.util.List;

/**
 * @author ok1996
 */
public interface DictService {
    /**
     * 获取指定类型的字典项列表
     * @param path
     * @return
     */
    List<DictItem> getDictItemList(String path);
}
