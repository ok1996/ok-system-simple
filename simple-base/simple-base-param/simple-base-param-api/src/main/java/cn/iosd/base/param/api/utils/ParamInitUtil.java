package cn.iosd.base.param.api.utils;

import cn.iosd.base.param.api.vo.CodeValue;
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

    /**
     * CodeValue类型
     */
    public static final TypeReference<List<CodeValue<?>>> CODE_VALUES_TYPE_REFERENCE = new TypeReference<List<CodeValue<?>>>() {
    };

    /**
     * 是否开启模拟数据-字段Key
     */
    public static final String OPEN_SIMULATION_CODE = "openSimulation";

    /**
     * 存储数据-字段Key
     */
    public static final String CONTENT_DATA_CODE = "contentData";

    /**
     * 该方法用于将传入的 CodeValue 对象中的 value 属性（泛型类型）转换成指定的 valueType 类型对象
     *
     * @param codeValueDto 需要转换的 CodeValue 对象
     * @param valueType    需要转换的目标类型
     * @return 转换后的指定类型对象
     */
    public static <T> T readValue(CodeValue<?> codeValueDto, Class<T> valueType) {
        return JsonMapper.convertObject(codeValueDto.getValue(), valueType);
    }

    /**
     * 该方法用于将传入的 CodeValue 对象中的 value 属性（泛型类型）转换成指定的 valueTypeRef 类型对象。
     *
     * @param codeValueDto 需要转换的 CodeValue 对象
     * @param valueTypeRef 需要转换的目标类型的 TypeReference 对象
     * @return 转换后的指定类型对象
     */
    public static <T> T readValue(CodeValue<?> codeValueDto, TypeReference<T> valueTypeRef) {
        return JsonMapper.convertObject(codeValueDto.getValue(), valueTypeRef);
    }

    /**
     * 该方法用于在传入的 CodeValue 列表中查找指定 code 对应的 value，并将其转换成指定类型的对象
     *
     * @param codeValueList CodeValue 列表
     * @param code          需要查找的 code 值
     * @param valueType     需要转换的目标类型
     * @return 查找到的 code 对应的 value 值转换成指定类型的对象
     */
    public static <T> T getValueByCode(List<CodeValue<?>> codeValueList, String code, Class<T> valueType) {
        Map<String, Object> codeValueMap = codeValueList.stream()
                .collect(Collectors.toMap(CodeValue::getCode, CodeValue::getValue));
        return JsonMapper.convertObject(codeValueMap.getOrDefault(code, null), valueType);
    }

    /**
     * 该方法用于在传入的 CodeValue 列表中查找指定 code 对应的 CodeValue 对象。
     *
     * @param codeValueList CodeValue 列表
     * @param code          需要查找的 code 值
     * @return 查找到的指定 code 对应的 CodeValue 对象，如果找不到则返回 Optional.empty()
     */
    public static Optional<CodeValue<?>> getCodeValueByCode(List<CodeValue<?>> codeValueList, String code) {
        return codeValueList.stream()
                .filter(v -> v.getCode().equals(code))
                .findFirst();
    }

    /**
     * 该方法用于在传入的 CodeValue 列表中查找指定 code 对应的 CodeValue 对象，然后返回该对象的 boolean 类型的值，如果找不到则返回 false。
     *
     * @param codeValueList CodeValue 列表
     * @param code          需要查找的 code 值
     * @return 查找到的指定 code 对应的 CodeValue 对象的 boolean 类型的值，如果找不到则返回 false
     */
    public static boolean getBooleanValueByCodeDefaultFalse(List<CodeValue<?>> codeValueList, String code) {
        return getCodeValueByCode(codeValueList, code)
                .map(CodeValue::getValue)
                .map(Boolean.class::cast)
                .orElse(false);
    }

    /**
     * 该方法用于在传入的 CodeValue 列表中查找 是否开启模拟数据code 对应的 CodeValue 对象，然后返回该对象的 boolean 类型的值，如果找不到则返回 false。
     *
     * @param codeValueList CodeValue 列表
     * @return 查找到 是否开启模拟数据code 对应的 CodeValue 对象的 boolean 类型的值，如果找不到则返回 false
     */
    public static boolean getBooleanValueByOpenSimulationCodeDefaultFalse(List<CodeValue<?>> codeValueList) {
        return getBooleanValueByCodeDefaultFalse(codeValueList, OPEN_SIMULATION_CODE);
    }
}
