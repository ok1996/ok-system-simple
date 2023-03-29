package cn.iosd.starter.dict.service.impl;

import cn.iosd.starter.dict.service.DictService;
import cn.iosd.starter.dict.vo.DictItem;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 从本地文件读取字典项列表
 *
 * @author ok1996
 */
@Service
public class LocalDictServiceImpl implements DictService {

    @Override
    public List<DictItem> getDictItemList(String path) {

        List<DictItem> dictItemList = null;
        File file = new File(path);
        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
            ObjectMapper mapper = new ObjectMapper();
            dictItemList = mapper.readValue(inputStream, new TypeReference<List<DictItem>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dictItemList;
    }
}
