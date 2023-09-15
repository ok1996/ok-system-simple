package cn.iosd.base.dict.api.feign;

import cn.iosd.base.dict.api.domain.DictInfo;
import cn.iosd.base.dict.api.vo.DictGroupVo;
import cn.iosd.starter.dict.vo.DictItem;
import cn.iosd.starter.web.base.CrudOperations;
import cn.iosd.starter.web.domain.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author ok1996
 */
@FeignClient(name = "simple-base-dict-service", contextId = "baseDictServiceFeign", path = "/simple-base-dict-service/DictInfo"
        , url = "${simple.feign.base.dict.url:}", primary = false)
public interface DictInfoFeign extends CrudOperations<DictInfo> {

    /**
     * 获取指定类型的字典项列表
     *
     * @param param 接口实现类所需的参数类型
     * @return 参数类型对应字典列表
     */
    @GetMapping("/custom/{param}")
    Response<List<DictItem>> getDictItemList(@PathVariable String param);

    /**
     * 提取已存在数据的字典分组
     *
     * @return 字典分组数据
     */
    @GetMapping("/uniqueDictGroups")
    public Response<List<DictGroupVo>> uniqueDictGroups();
}