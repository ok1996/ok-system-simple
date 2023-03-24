package cn.iosd.base.param.service.impl;

import cn.iosd.base.param.domain.BaseParam;
import cn.iosd.base.param.mapper.BaseParamMapper;
import cn.iosd.base.param.service.IBaseParamService;
import cn.iosd.base.param.utils.ParamInitUtil;
import cn.iosd.base.param.vo.BaseParamCodeValueVo;
import cn.iosd.base.param.vo.BaseParamSaveReqVo;
import cn.iosd.starter.web.utils.JsonUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ok1996
 */
@Service
public class BaseParamServiceImpl extends ServiceImpl<BaseParamMapper, BaseParam> implements IBaseParamService {

    @Override
    public BaseParam selectBaseParamById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public BaseParam selectBaseParamByKey(String paramKey) {
        BaseParam baseParam = new BaseParam();
        baseParam.setParamKey(paramKey);
        return baseMapper.selectOne(Wrappers.lambdaQuery(baseParam));
    }

    @Override
    public List<BaseParamCodeValueVo<?>> selectCodeValueVoParamByKey(String paramKey) throws JsonProcessingException {
        BaseParam baseParam = selectBaseParamByKey(paramKey);
        return JsonUtil.convertObject(baseParam.getCodeValues(), ParamInitUtil.CODE_VALUES_TYPE_REFERENCE);
    }


    @Override
    public int insertBaseParam(BaseParamSaveReqVo baseParamVo) throws JsonProcessingException {
        BaseParam baseParam = convertBaseParam(baseParamVo);
        LocalDateTime now = LocalDateTime.now();
        baseParam.setCreateTime(now);
        baseParam.setModifyTime(now);
        baseParam.setHistoryCodeValues(inputHistoryCodeValues(baseParam.getCodeValues(), null));
        return baseMapper.insert(baseParam);
    }

    @Override
    public int updateBaseParam(BaseParamSaveReqVo baseParamVo) throws JsonProcessingException {
        BaseParam baseParam = convertBaseParam(baseParamVo);
        LocalDateTime now = LocalDateTime.now();
        baseParam.setModifyTime(now);

        JsonNode historyCodeValues = null;
        if (baseParamVo.getId() != null) {
            historyCodeValues = baseMapper.selectById(baseParamVo.getId()).getHistoryCodeValues();
        }

        baseParam.setHistoryCodeValues(inputHistoryCodeValues(baseParam.getCodeValues(), historyCodeValues));

        return baseMapper.updateById(baseParam);
    }

    /**
     * 请求实体转换Domain
     *
     * @param baseParamVo
     * @return
     * @throws JsonProcessingException
     */
    public BaseParam convertBaseParam(BaseParamSaveReqVo baseParamVo) throws JsonProcessingException {
        BaseParam baseParam = new BaseParam();
        JsonNode moduleNames = JsonUtil.convertObject(baseParamVo.getModuleNames(), JsonNode.class);
        JsonNode codeValues = JsonUtil.convertObject(baseParamVo.getCodeValues(), JsonNode.class);
        baseParam.setId(baseParamVo.getId());
        baseParam.setModuleNames(moduleNames);
        baseParam.setCodeValues(codeValues);
        baseParam.setRemark(baseParamVo.getRemark());
        baseParam.setParamKey(baseParamVo.getParamKey());
        return baseParam;
    }

    /**
     * 更新历史值
     *
     * @param codeValues
     * @param historyCodeValues
     * @return
     */
    public JsonNode inputHistoryCodeValues(JsonNode codeValues, JsonNode historyCodeValues) {
        List<JsonNode> subNodes = new ArrayList<>();
        if (historyCodeValues != null && historyCodeValues.isArray()) {
            for (final JsonNode objNode : historyCodeValues) {
                subNodes.add(objNode);
            }
        }
        ObjectNode codeValuesObject = new ObjectNode(JsonNodeFactory.instance);
        codeValuesObject.set("codeValues", codeValues);
        codeValuesObject.put("createTime", Timestamp.valueOf(LocalDateTime.now()).getTime());
        subNodes.add(codeValuesObject);
        return new ArrayNode(JsonNodeFactory.instance, subNodes.size() > 10 ? subNodes.subList(subNodes.size() - 10, subNodes.size()) : subNodes);
    }

}
