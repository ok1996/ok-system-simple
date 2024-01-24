package cn.iosd.base.param.api.utils;

import cn.iosd.base.param.api.vo.CodeValue;
import cn.iosd.base.param.api.vo.CodeValueListHistory;
import cn.iosd.utils.jackson.JsonMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;
import java.util.Optional;

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
     * CodeValueListHistory类型
     */
    public static final TypeReference<List<CodeValueListHistory>> CODE_VALUES_HISTORY_TYPE_REFERENCE = new TypeReference<List<CodeValueListHistory>>() {
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
     * 列表中查找指定code对应的值，并将其转换成指定类型的对象
     *
     * @param codeValueList 列表
     * @param code          需要查找的code值
     * @param valueTypeRef  需要转换的目标类型
     * @return 查找到的 code 对应的 value 值转换成指定类型的对象，不存在时返回空
     */
    public static <T> T findFirstByCode(List<CodeValue<?>> codeValueList, String code, TypeReference<T> valueTypeRef, T defaultValue) {
        return findFirst(codeValueList, code)
                .map(v -> JsonMapper.convertObject(v.getValue(), valueTypeRef))
                .orElse(defaultValue);
    }

    /**
     * 列表中查找指定code对应的值，并将其转换成指定类型的对象
     *
     * @param codeValueList 列表
     * @param code          需要查找的code值
     * @param valueType     需要转换的目标类型
     * @return 查找到的 code 对应的 value 值转换成指定类型的对象，不存在时返回空
     */
    public static <T> T findFirstByCode(List<CodeValue<?>> codeValueList, String code, Class<T> valueType, T defaultValue) {
        return findFirst(codeValueList, code)
                .map(v -> JsonMapper.convertObject(v.getValue(), valueType))
                .orElse(defaultValue);
    }


    /**
     * 列表中查找指定code对应的值并转换为boolean类型，如果找不到则返回 defaultValue
     *
     * @param codeValueList 列表
     * @param code          需要查找的 code 值
     * @return 查找到的 code 对应的 value 值转换成boolean类型的对象，不存在时返回 defaultValue
     */
    public static boolean findFirstByCode(List<CodeValue<?>> codeValueList, String code, boolean defaultValue) {
        return findFirst(codeValueList, code)
                .map(CodeValue::getValue)
                .map(Boolean.class::cast)
                .orElse(defaultValue);
    }

    /**
     * 该方法用于在传入的 CodeValue 列表中查找指定 code 对应的 CodeValue 对象
     *
     * @param codeValueList CodeValue 列表
     * @param code          需要查找的 code 值
     * @return 查找到的指定 code 对应的 CodeValue 对象，如果找不到则返回 Optional.empty()
     */
    public static Optional<CodeValue<?>> findFirst(List<CodeValue<?>> codeValueList, String code) {
        return codeValueList.stream()
                .filter(v -> v.getCode().equals(code))
                .findFirst();
    }

    public static List<CodeValue<?>> readValueList(String codeValuesString) throws JsonProcessingException {
        return JsonMapper.readValue(codeValuesString, CODE_VALUES_TYPE_REFERENCE);
    }

    public static List<CodeValueListHistory> readValueListHistory(String codeValuesHistoryString) throws JsonProcessingException {
        return JsonMapper.readValue(codeValuesHistoryString, CODE_VALUES_HISTORY_TYPE_REFERENCE);
    }

}
