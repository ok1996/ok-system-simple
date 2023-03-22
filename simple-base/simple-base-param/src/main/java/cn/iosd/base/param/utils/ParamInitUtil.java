package cn.iosd.base.param.utils;

import cn.iosd.base.param.vo.BaseParamCodeValueVo;
import cn.iosd.starter.web.utils.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author ok1996
 */
public class ParamInitUtil {

    public static final TypeReference<List<BaseParamCodeValueVo<?>>> CODE_VALUES_TYPE_REFERENCE = new TypeReference<List<BaseParamCodeValueVo<?>>>() {
    };

    /**
     * ?转t
     *
     * @param paramSingleCodeValueDto
     * @param valueType
     * @param <T>
     * @return
     */
    public static <T> T readValue(BaseParamCodeValueVo<?> paramSingleCodeValueDto, Class<T> valueType) throws JsonProcessingException {
        return JsonUtil.DEFAULT_INSTANCE.readValue(JsonUtil.DEFAULT_INSTANCE.writeValueAsString(paramSingleCodeValueDto.getValue()), valueType);
    }

    /**
     * ?转t
     *
     * @param paramSingleCodeValueDto
     * @param valueTypeRef
     * @param <T>
     * @return
     */
    public static <T> T readValue(BaseParamCodeValueVo<?> paramSingleCodeValueDto, TypeReference<T> valueTypeRef) throws JsonProcessingException {
        return JsonUtil.DEFAULT_INSTANCE.readValue(JsonUtil.DEFAULT_INSTANCE.writeValueAsString(paramSingleCodeValueDto.getValue()), valueTypeRef);
    }

    /**
     * 根据code获取value值
     *
     * @param simulation
     * @param code
     * @param valueType
     * @param <T>
     * @return
     * @throws JsonProcessingException
     */
    public static <T> T getValueByCode(List<BaseParamCodeValueVo<?>> simulation, String code, Class<T> valueType) throws JsonProcessingException {
        Map<String, Object> codeValueMap = simulation.stream()
                .collect(Collectors.toMap(BaseParamCodeValueVo::getCode, BaseParamCodeValueVo::getValue));
        return JsonUtil.DEFAULT_INSTANCE.readValue(JsonUtil.DEFAULT_INSTANCE.writeValueAsString(codeValueMap.getOrDefault(code, null)), valueType);
    }

    /**
     * 从List列表中根据code获取BaseParamCodeValueVo
     *
     * @param simulation 数据列表
     * @param code
     * @return
     */
    public static Optional<BaseParamCodeValueVo<?>> getBaseParamCodeValueVoByCode(List<BaseParamCodeValueVo<?>> simulation, String code) {
        return simulation.stream()
                .filter(v -> v.getCode().equals(code))
                .findFirst();
    }

    /**
     * 从List列表中根据code获取BaseParamCodeValueVo中的Value值（boolean类型）
     * <p>code在数据列表中不存在时返回的默认值</p>
     *
     * @param simulation 数据列表
     * @param code
     * @return
     */
    public static boolean getBooleanValueByCodeDefaultFalse(List<BaseParamCodeValueVo<?>> simulation, String code) {
        return getBaseParamCodeValueVoByCode(simulation, code)
                .map(BaseParamCodeValueVo::getValue)
                .map(Boolean.class::cast)
                .orElse(false);
    }

}
