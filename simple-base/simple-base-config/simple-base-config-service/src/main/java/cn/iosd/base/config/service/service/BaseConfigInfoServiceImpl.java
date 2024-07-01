package cn.iosd.base.config.service.service;

import cn.iosd.base.config.api.domain.BaseConfigInfo;
import cn.iosd.base.config.api.exception.BaseConfigException;
import cn.iosd.base.config.api.service.IBaseConfigService;
import cn.iosd.base.config.api.utils.ConfigUtils;
import cn.iosd.base.config.api.vo.BaseConfigVo;
import cn.iosd.base.config.api.vo.CodeValue;
import cn.iosd.base.config.api.vo.CodeValueListHistory;
import cn.iosd.base.config.service.entity.BaseConfigInfoEntity;
import cn.iosd.base.config.service.mapper.BaseConfigInfoMapper;
import cn.iosd.starter.datasource.base.BaseServiceImpl;
import cn.iosd.utils.jackson.JsonMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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
public class BaseConfigInfoServiceImpl extends BaseServiceImpl<BaseConfigInfoMapper, BaseConfigInfoEntity, BaseConfigInfo> implements IBaseConfigService {

    /**
     * 历史记录列表保留数
     */
    public static final Integer HISTORY_RETENTION_SIZE = 10;

    @Override
    public BaseConfigInfo selectByKey(String key) {
        return baseMapper.selectOne(Wrappers.<BaseConfigInfoEntity>lambdaQuery()
                .eq(BaseConfigInfoEntity::getConfigKey, key));
    }

    @Override
    public List<CodeValue<?>> selectValueListByKey(String key) {
        BaseConfigInfo baseConfigInfo = selectByKey(key);
        try {
            return ConfigUtils.readValueList(baseConfigInfo.getCodeValues());
        } catch (JsonProcessingException e) {
            throw new BaseConfigException("key:" + key + "，在反序列化List<CodeValue<?>>过程中出现错误", e);
        }
    }

    @Override
    public int insert(BaseConfigVo baseConfigVo) {
        BaseConfigInfoEntity baseConfig = convertBaseParam(baseConfigVo);
        LocalDateTime now = LocalDateTime.now();
        baseConfig.setCreateTime(now);
        baseConfig.setModifyTime(now);
        return baseMapper.insert(baseConfig);
    }

    @Override
    public int update(BaseConfigVo baseConfigVo) {
        BaseConfigInfoEntity baseConfig = convertBaseParam(baseConfigVo);
        LocalDateTime now = LocalDateTime.now();
        baseConfig.setModifyTime(now);
        return baseMapper.updateById(baseConfig);
    }

    @Override
    public List<CodeValueListHistory> selectValueListHistoryByKey(String key) {
        BaseConfigInfo baseConfigInfo = selectByKey(key);
        try {
            return ConfigUtils.readValueListHistory(baseConfigInfo.getHistoryCodeValues());
        } catch (JsonProcessingException e) {
            throw new BaseConfigException("key:" + key + "，在反序列化List<CodeValueListHistory>过程中出现错误", e);
        }
    }

    /**
     * 请求实体转换Domain
     *
     * @param baseConfigVo 请求实体
     * @return 转换后的Entity实体
     */
    public BaseConfigInfoEntity convertBaseParam(BaseConfigVo baseConfigVo) {
        BaseConfigInfoEntity baseConfig = new BaseConfigInfoEntity();
        baseConfig.setId(baseConfigVo.getId());

        String codeValueListHistory = Optional.ofNullable(baseConfigVo.getId())
                .map(baseMapper::selectById)
                .map(BaseConfigInfoEntity::getHistoryCodeValues)
                .orElse(null);
        try {
            baseConfig.setModuleNames(JsonMapper.getObjectMapper().writeValueAsString(baseConfigVo.getModuleNames()));
            baseConfig.setCodeValues((JsonMapper.getObjectMapper().writeValueAsString(baseConfigVo.getCodeValues())));
            baseConfig.setHistoryCodeValues(inputHistoryCodeValues(baseConfigVo.getCodeValues(), codeValueListHistory));
        } catch (JsonProcessingException e) {
            throw new BaseConfigException("id:" + baseConfigVo.getId() + "，实体转换反序列化过程中出现错误", e);
        }
        baseConfig.setRemark(baseConfigVo.getRemark());
        baseConfig.setConfigKey(baseConfigVo.getParamKey());
        return baseConfig;
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
        if (!StringUtils.isBlank(codeValueListHistory)) {
            resHistoryObj = JsonMapper.readValue(codeValueListHistory, new TypeReference<List<CodeValueListHistory>>() {
            });
        }
        CodeValueListHistory cvHistoryToAdd = new CodeValueListHistory();
        cvHistoryToAdd.setTime(System.currentTimeMillis());
        cvHistoryToAdd.setCodeValueList(codeValueList);
        resHistoryObj.add(cvHistoryToAdd);
        if (resHistoryObj.size() > HISTORY_RETENTION_SIZE) {
            resHistoryObj = resHistoryObj.subList(resHistoryObj.size() - HISTORY_RETENTION_SIZE, resHistoryObj.size());
        }
        return JsonMapper.getObjectMapper().writeValueAsString(resHistoryObj);
    }

}
