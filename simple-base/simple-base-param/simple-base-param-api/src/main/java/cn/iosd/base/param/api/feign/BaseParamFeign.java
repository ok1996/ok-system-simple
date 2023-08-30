package cn.iosd.base.param.api.feign;

import cn.iosd.base.param.api.domain.BaseParam;
import cn.iosd.base.param.api.vo.BaseParamVo;
import cn.iosd.base.param.api.vo.CodeValue;
import cn.iosd.base.param.api.vo.CodeValueListHistory;
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
@FeignClient(name = "simple-base-param-service", contextId = "baseParamServiceFeign", path = "/simple-base-param-service/param"
        , url = "${simple.feign.base.param.url:}", primary = false)
public interface BaseParamFeign extends CrudOperations<BaseParam> {

    /**
     * 新增
     *
     * @param baseParamVo 参数实体
     * @return 返回结果
     */
    @PostMapping
    Response<Integer> insertBaseParam(@RequestBody BaseParamVo baseParamVo);

    /**
     * 修改
     *
     * @param baseParamVo 参数实体
     * @return 返回结果
     */
    @PutMapping
    Response<Integer> updateBaseParam(@RequestBody BaseParamVo baseParamVo);

    /**
     * 查询
     *
     * @param paramKey key
     * @return 数据实体
     */
    @GetMapping("/BaseParam/key/{paramKey}")
    Response<BaseParam> selectBaseParamByKey(@PathVariable String paramKey);

    /**
     * 查询参数配置
     *
     * @param paramKey key
     * @return 参数配置
     */
    @GetMapping("/CodeValues/key/{paramKey}")
    Response<List<CodeValue<?>>> selectCodeValueVoParamByKey(@PathVariable String paramKey);

    /**
     * 查询参数配置历史
     *
     * @param paramKey key
     * @return 参数配置历史
     */
    @GetMapping("/CodeValuesHistory/key/{paramKey}")
    Response<List<CodeValueListHistory>> selectCodeValueHistoryParamByKey(@PathVariable String paramKey);
}
