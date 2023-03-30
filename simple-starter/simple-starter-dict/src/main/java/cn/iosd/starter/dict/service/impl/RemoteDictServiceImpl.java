package cn.iosd.starter.dict.service.impl;

import cn.iosd.starter.dict.service.DictService;
import cn.iosd.starter.dict.vo.DictItem;
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

    @Override
    public List<DictItem> getDictItemList(String dictionaryParams) {
        String path = dictionaryParams;
        ResponseEntity<List<DictItem>> responseEntity = restTemplate.exchange(path, HttpMethod.GET, null, new ParameterizedTypeReference<List<DictItem>>() {
        });
        return responseEntity.getBody();
    }
}