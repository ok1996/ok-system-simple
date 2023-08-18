package cn.iosd.base.param.service.service;

import cn.iosd.base.param.api.domain.BaseParam;
import cn.iosd.base.param.api.service.IBaseParamService;
import cn.iosd.base.param.api.utils.ParamInitUtil;
import cn.iosd.base.param.api.vo.BaseParamVo;
import cn.iosd.base.param.api.vo.CodeValue;
import cn.iosd.base.param.service.entity.BaseParamEntity;
import cn.iosd.base.param.service.mapper.BaseParamMapper;
import cn.iosd.utils.JsonMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ok1996
 */
@Service
@Primary
public class BaseParamServiceImpl extends ServiceImpl<BaseParamMapper, BaseParamEntity> implements IBaseParamService {

    @Override
    public BaseParam selectBaseParamByKey(String paramKey) {
        BaseParamEntity queryDb = new BaseParamEntity();
        queryDb.setParamKey(paramKey);
        return baseMapper.selectOne(Wrappers.lambdaQuery(queryDb));
    }

    @Override
    public List<CodeValue<?>> selectCodeValueVoParamByKey(String paramKey) {
        BaseParam baseParam = selectBaseParamByKey(paramKey);
        try {
            return JsonMapper.readValue(baseParam.getCodeValues(), ParamInitUtil.CODE_VALUES_TYPE_REFERENCE);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("paramKey:" + paramKey + "，在反序列化List<CodeValue<?>>过程中出现错误", e);
        }
    }

    @Override
    public int insertBaseParam(BaseParamVo baseParamVo) {
        BaseParamEntity baseParam = convertBaseParam(baseParamVo);
        LocalDateTime now = LocalDateTime.now();
        baseParam.setCreateTime(now);
        baseParam.setModifyTime(now);
        return baseMapper.insert(baseParam);
    }

    @Override
    public int updateBaseParam(BaseParamVo baseParamVo) {
        BaseParamEntity baseParam = convertBaseParam(baseParamVo);
        LocalDateTime now = LocalDateTime.now();
        baseParam.setModifyTime(now);
        return baseMapper.updateById(baseParam);
    }

    /**
     * 请求实体转换Domain
     *
     * @param baseParamVo
     * @return
     */
    public BaseParamEntity convertBaseParam(BaseParamVo baseParamVo) {
        BaseParamEntity baseParam = new BaseParamEntity();
        baseParam.setId(baseParamVo.getId());
        try {
            baseParam.setModuleNames(JsonMapper.getObjectMapper().writeValueAsString(baseParamVo.getModuleNames()));
            baseParam.setCodeValues((JsonMapper.getObjectMapper().writeValueAsString(baseParamVo.getCodeValues())));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        baseParam.setRemark(baseParamVo.getRemark());
        baseParam.setParamKey(baseParamVo.getParamKey());
        return baseParam;
    }

}
