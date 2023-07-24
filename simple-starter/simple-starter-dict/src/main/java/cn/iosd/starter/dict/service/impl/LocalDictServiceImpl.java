package cn.iosd.starter.dict.service.impl;

import cn.iosd.starter.dict.service.DictService;
import cn.iosd.starter.dict.vo.DictItem;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 从本地文件读取字典项列表
 *
 * @author ok1996
 */
@Service
public class LocalDictServiceImpl implements DictService {

    /**
     * 本地字典文件目录
     */
    @Value("${simple.dict.resourceDictFileDir:/dict.json}")
    private String resourceDictFileDir;

    /**
     * 用于反序列化字典项的类型引用
     */
    public static final TypeReference<Map<String, List<DictItem>>> TYPE_DICT_ITEM = new TypeReference<Map<String, List<DictItem>>>() {
    };

    @Override
    public List<DictItem> getDictItemList(String dictionaryParams) {
        try (InputStream inputStream = this.getClass().getResourceAsStream(resourceDictFileDir)) {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, List<DictItem>> dictItemList = mapper.readValue(inputStream, TYPE_DICT_ITEM);
            return dictItemList.get(dictionaryParams);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
