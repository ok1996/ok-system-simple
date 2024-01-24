package cn.iosd.base.config.api.feign;

import cn.iosd.base.config.api.domain.BaseConfigInfo;
import cn.iosd.base.config.api.vo.BaseConfigVo;
import cn.iosd.base.config.api.vo.CodeValue;
import cn.iosd.base.config.api.vo.CodeValueListHistory;
import cn.iosd.starter.web.base.CrudOperations;
import cn.iosd.starter.web.domain.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author ok1996
 */
@FeignClient(name = "simple-base-config-service", contextId = "baseConfigInfoFeign", path = "/simple-base-config-service/configInfo"
        , url = "${simple.feign.base.config.url:}", primary = false)
public interface BaseConfigInfoFeign extends CrudOperations<BaseConfigInfo> {

    /**
     * 新增
     *
     * @param baseConfigVo 参数实体
     * @return 返回结果
     */
    @PostMapping
    Response<Integer> insert(@RequestBody BaseConfigVo baseConfigVo);

    /**
     * 修改
     *
     * @param baseConfigVo 参数实体
     * @return 返回结果
     */
    @PutMapping
    Response<Integer> update(@RequestBody BaseConfigVo baseConfigVo);

    /**
     * 查询
     *
     * @param key key
     * @return 数据实体
     */
    @GetMapping("/key/{key}")
    Response<BaseConfigInfo> selectByKey(@PathVariable String key);

    /**
     * 查询参数配置
     *
     * @param key key
     * @return 参数配置
     */
    @GetMapping("/CodeValues/key/{key}")
    Response<List<CodeValue<?>>> selectListByKey(@PathVariable String key);

    /**
     * 查询参数配置历史
     *
     * @param key key
     * @return 参数配置历史
     */
    @GetMapping("/CodeValuesHistory/key/{paramKey}")
    Response<List<CodeValueListHistory>> selectListHistoryByKey(@PathVariable String key);
}
