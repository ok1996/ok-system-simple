package cn.iosd.demo.dict.service.dict;

import cn.iosd.starter.dict.service.DictService;
import cn.iosd.starter.dict.vo.DictItem;
import cn.iosd.starter.web.domain.Response;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 自定义实现获取指定类型的字典项列表
 *
 * @author ok1996
 */
@Service
public class CustomDictServiceImpl implements DictService {

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<DictItem> getDictItemList(String dictionaryParams) {
        String path = "http://127.0.0.1:11120/simple-demo-dict/dict/custom/" + dictionaryParams;
        ResponseEntity<Response<List<DictItem>>> responseEntity = restTemplate.exchange(path, HttpMethod.GET, null, new ParameterizedTypeReference<Response<List<DictItem>>>() {
        });
        return responseEntity.getBody().getData();
    }
}