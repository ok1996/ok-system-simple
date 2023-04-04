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

    @Override
    public List<DictItem> getDictItemList(String dictionaryParams) {

        Map<String, List<DictItem>> dictItemList = null;
        try (InputStream inputStream = this.getClass().getResourceAsStream(resourceDictFileDir)) {
            ObjectMapper mapper = new ObjectMapper();
            dictItemList = mapper.readValue(inputStream, new TypeReference<Map<String, List<DictItem>>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dictItemList.get(dictionaryParams);
    }
}
