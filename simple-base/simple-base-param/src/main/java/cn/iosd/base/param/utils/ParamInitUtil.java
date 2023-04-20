package cn.iosd.base.param.utils;

import cn.iosd.base.param.vo.BaseParamCodeValueVo;
import cn.iosd.utils.JsonMapper;
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
     * 该方法用于将传入的 BaseParamCodeValueVo 对象中的 value 属性（泛型类型）转换成指定的 valueType 类型对象
     *
     * @param paramSingleCodeValueDto 需要转换的 BaseParamCodeValueVo 对象
     * @param valueType               需要转换的目标类型
     * @return 转换后的指定类型对象
     */
    public static <T> T readValue(BaseParamCodeValueVo<?> paramSingleCodeValueDto, Class<T> valueType) {
        return JsonMapper.convertObject(paramSingleCodeValueDto.getValue(), valueType);
    }

    /**
     * 该方法用于将传入的 BaseParamCodeValueVo 对象中的 value 属性（泛型类型）转换成指定的 valueTypeRef 类型对象。
     *
     * @param paramSingleCodeValueDto 需要转换的 BaseParamCodeValueVo 对象
     * @param valueTypeRef            需要转换的目标类型的 TypeReference 对象
     * @return 转换后的指定类型对象
     */
    public static <T> T readValue(BaseParamCodeValueVo<?> paramSingleCodeValueDto, TypeReference<T> valueTypeRef) {
        return JsonMapper.convertObject(paramSingleCodeValueDto.getValue(), valueTypeRef);
    }

    /**
     * 该方法用于在传入的 BaseParamCodeValueVo 列表中查找指定 code 对应的 value，并将其转换成指定类型的对象
     *
     * @param simulation BaseParamCodeValueVo 列表
     * @param code       需要查找的 code 值
     * @param valueType  需要转换的目标类型
     * @return 查找到的 code 对应的 value 值转换成指定类型的对象
     */
    public static <T> T getValueByCode(List<BaseParamCodeValueVo<?>> simulation, String code, Class<T> valueType) {
        Map<String, Object> codeValueMap = simulation.stream()
                .collect(Collectors.toMap(BaseParamCodeValueVo::getCode, BaseParamCodeValueVo::getValue));
        return JsonMapper.convertObject(codeValueMap.getOrDefault(code, null), valueType);
    }

    /**
     * 该方法用于在传入的 BaseParamCodeValueVo 列表中查找指定 code 对应的 BaseParamCodeValueVo 对象。
     *
     * @param simulation BaseParamCodeValueVo 列表
     * @param code       需要查找的 code 值
     * @return 查找到的指定 code 对应的 BaseParamCodeValueVo 对象，如果找不到则返回 Optional.empty()
     */
    public static Optional<BaseParamCodeValueVo<?>> getBaseParamCodeValueVoByCode(List<BaseParamCodeValueVo<?>> simulation, String code) {
        return simulation.stream()
                .filter(v -> v.getCode().equals(code))
                .findFirst();
    }

    /**
     * 该方法用于在传入的 BaseParamCodeValueVo 列表中查找指定 code 对应的 BaseParamCodeValueVo 对象，然后返回该对象的 boolean 类型的值，如果找不到则返回 false。
     *
     * @param simulation BaseParamCodeValueVo 列表
     * @param code       需要查找的 code 值
     * @return 查找到的指定 code 对应的 BaseParamCodeValueVo 对象的 boolean 类型的值，如果找不到则返回 false
     */
    public static boolean getBooleanValueByCodeDefaultFalse(List<BaseParamCodeValueVo<?>> simulation, String code) {
        return getBaseParamCodeValueVoByCode(simulation, code)
                .map(BaseParamCodeValueVo::getValue)
                .map(Boolean.class::cast)
                .orElse(false);
    }

}
