package cn.iosd.base.param.utils;

import cn.iosd.base.param.vo.BaseParamCodeValueVo;
import cn.iosd.starter.web.utils.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;
import java.util.Optional;

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
        String str = JsonUtil.DEFAULT_INSTANCE.writeValueAsString(paramSingleCodeValueDto.getValue());
        return JsonUtil.DEFAULT_INSTANCE.readValue(str, valueType);
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
        String str = JsonUtil.DEFAULT_INSTANCE.writeValueAsString(paramSingleCodeValueDto.getValue());
        return JsonUtil.DEFAULT_INSTANCE.readValue(str, valueTypeRef);
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
        Optional<BaseParamCodeValueVo<?>> singleCodeValueDto = simulation.stream().filter(v ->
                v.getCode().equals(code)
        ).findFirst();
        if (singleCodeValueDto.isPresent()) {
            String str = JsonUtil.DEFAULT_INSTANCE.writeValueAsString(singleCodeValueDto.get().getValue());
            return JsonUtil.DEFAULT_INSTANCE.readValue(str, valueType);
        }
        return null;
    }

    /**
     * 从List列表中根据code获取BaseParamCodeValueVo
     *
     * @param simulation 数据列表
     * @param code
     * @return
     */
    public static Optional<BaseParamCodeValueVo<?>> getBaseParamCodeValueVoByCode(List<BaseParamCodeValueVo<?>> simulation, String code) {
        return simulation.stream().filter(v -> v.getCode().equals(code)).findFirst();
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
        Optional<BaseParamCodeValueVo<?>> singleCodeValueDto = simulation.stream().filter(v ->
                v.getCode().equals(code)
        ).findFirst();
        if (singleCodeValueDto.isPresent() && (Boolean) singleCodeValueDto.get().getValue()) {
            return true;
        }
        return false;
    }
}
