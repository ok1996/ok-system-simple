package cn.iosd.starter.dict.service.impl;

import cn.iosd.starter.dict.service.DictService;
import cn.iosd.starter.dict.vo.DictItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 调用远程接口获取字典项列表
 *
 * @author ok1996
 */
@Service
public class RemoteDictServiceImpl implements DictService {

    private RestTemplate restTemplate = new RestTemplate();

    @Value("${simple.dict.remoteBaseUrl:}")
    private String remoteBaseUrl;

    /**
     * 用于反序列化字典项的类型引用
     */
    public static final ParameterizedTypeReference<List<DictItem>> TYPE_DICT_ITEM = new ParameterizedTypeReference<List<DictItem>>() {
    };

    @Override
    public List<DictItem> getDictItemList(String dictionaryParams) {
        String path = remoteBaseUrl + dictionaryParams;
        ResponseEntity<List<DictItem>> responseEntity = restTemplate.exchange(path, HttpMethod.GET, null, TYPE_DICT_ITEM);
        return responseEntity.getBody();
    }
}