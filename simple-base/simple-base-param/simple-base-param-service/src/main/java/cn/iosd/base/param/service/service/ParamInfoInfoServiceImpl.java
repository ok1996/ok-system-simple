package cn.iosd.base.param.service.service;

import cn.iosd.base.param.api.domain.ParamInfo;
import cn.iosd.base.param.api.service.IParamInfoService;
import cn.iosd.base.param.api.utils.ParamInitUtil;
import cn.iosd.base.param.api.vo.BaseParamVo;
import cn.iosd.base.param.api.vo.CodeValue;
import cn.iosd.base.param.api.vo.CodeValueListHistory;
import cn.iosd.base.param.service.entity.ParamInfoEntity;
import cn.iosd.base.param.service.mapper.ParamInfoMapper;
import cn.iosd.utils.JsonMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author ok1996
 */
@Service
@Primary
public class ParamInfoInfoServiceImpl extends ServiceImpl<ParamInfoMapper, ParamInfoEntity> implements IParamInfoService {

    @Override
    public ParamInfo selectBaseParamByKey(String paramKey) {
        ParamInfoEntity queryDb = new ParamInfoEntity();
        queryDb.setParamKey(paramKey);
        return baseMapper.selectOne(Wrappers.lambdaQuery(queryDb));
    }

    @Override
    public List<CodeValue<?>> selectCodeValueVoParamByKey(String paramKey) {
        ParamInfo paramInfo = selectBaseParamByKey(paramKey);
        try {
            return JsonMapper.readValue(paramInfo.getCodeValues(), ParamInitUtil.CODE_VALUES_TYPE_REFERENCE);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("paramKey:" + paramKey + "，在反序列化List<CodeValue<?>>过程中出现错误", e);
        }
    }

    @Override
    public int insertBaseParam(BaseParamVo baseParamVo) {
        ParamInfoEntity baseParam = convertBaseParam(baseParamVo);
        LocalDateTime now = LocalDateTime.now();
        baseParam.setCreateTime(now);
        baseParam.setModifyTime(now);
        return baseMapper.insert(baseParam);
    }

    @Override
    public int updateBaseParam(BaseParamVo baseParamVo) {
        ParamInfoEntity baseParam = convertBaseParam(baseParamVo);
        LocalDateTime now = LocalDateTime.now();
        baseParam.setModifyTime(now);
        return baseMapper.updateById(baseParam);
    }

    @Override
    public List<CodeValueListHistory> selectCodeValueHistoryParamByKey(String paramKey) {
        ParamInfo paramInfo = selectBaseParamByKey(paramKey);
        try {
            return JsonMapper.readValue(paramInfo.getHistoryCodeValues(), new TypeReference<List<CodeValueListHistory>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException("paramKey:" + paramKey + "，在反序列化List<CodeValueListHistory>过程中出现错误", e);
        }
    }

    /**
     * 请求实体转换Domain
     *
     * @param baseParamVo 请求实体
     * @return 转换后的Entity实体
     */
    public ParamInfoEntity convertBaseParam(BaseParamVo baseParamVo) {
        ParamInfoEntity baseParam = new ParamInfoEntity();
        baseParam.setId(baseParamVo.getId());

        String codeValueListHistory = Optional.ofNullable(baseParamVo.getId())
                .map(baseMapper::selectById)
                .map(ParamInfoEntity::getHistoryCodeValues)
                .orElse(null);
        try {
            baseParam.setModuleNames(JsonMapper.getObjectMapper().writeValueAsString(baseParamVo.getModuleNames()));
            baseParam.setCodeValues((JsonMapper.getObjectMapper().writeValueAsString(baseParamVo.getCodeValues())));
            baseParam.setHistoryCodeValues(inputHistoryCodeValues(baseParamVo.getCodeValues(), codeValueListHistory));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        baseParam.setRemark(baseParamVo.getRemark());
        baseParam.setParamKey(baseParamVo.getParamKey());
        return baseParam;
    }


    /**
     * 将新值添加到历史配置值字符串
     *
     * @param codeValueList        新配置值
     * @param codeValueListHistory 历史配置值字符串
     * @return 增加了新值的历史配置值字符串
     * @throws JsonProcessingException JSON处理异常
     */
    public String inputHistoryCodeValues(List<CodeValue<?>> codeValueList, String codeValueListHistory) throws JsonProcessingException {
        List<CodeValueListHistory> resHistoryObj = new ArrayList<>();
        Integer historySize = 10;
        if (!StringUtils.isBlank(codeValueListHistory)) {
            resHistoryObj = JsonMapper.readValue(codeValueListHistory, new TypeReference<List<CodeValueListHistory>>() {
            });
        }
        CodeValueListHistory cvHistoryToAdd = new CodeValueListHistory();
        cvHistoryToAdd.setTime(System.currentTimeMillis());
        cvHistoryToAdd.setCodeValueList(codeValueList);
        resHistoryObj.add(cvHistoryToAdd);
        if (resHistoryObj.size() > historySize) {
            resHistoryObj = resHistoryObj.subList(resHistoryObj.size() - historySize, resHistoryObj.size());
        }
        return JsonMapper.getObjectMapper().writeValueAsString(resHistoryObj);
    }

}
