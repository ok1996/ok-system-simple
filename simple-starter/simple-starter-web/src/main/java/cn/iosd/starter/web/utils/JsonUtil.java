package cn.iosd.starter.web.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * @author ok1996
 */
public class JsonUtil {
    public static SimpleModule simpleModule = new SimpleModule();

    public static final ObjectMapper NOT_NULL_INSTANCE = new ObjectMapper()
            // 只输出非 null 字段
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            // 当实体类中不含有 json 字符串的某些字段时，不抛出异常
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            // 非 bean 对象不抛异常
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            // BigDecimal 精度
            .configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true)
            // 禁用科学计数法
            .configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true)
            // 允许使用注释
            .configure(JsonParser.Feature.ALLOW_COMMENTS, true)
            // 允许字段名没有引号
            .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
            // 允许单引号
            .configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)
            .registerModule(simpleModule);

    public static final ObjectMapper DEFAULT_INSTANCE = new ObjectMapper()
            // 当实体类中不含有 json 字符串的某些字段时，不抛出异常
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            // 非 bean 对象不抛异常
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            // BigDecimal 精度
            .configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true)
            // 禁用科学计数法
            .configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true)
            // 允许使用注释
            .configure(JsonParser.Feature.ALLOW_COMMENTS, true)
            // 允许字段名没有引号
            .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
            // 允许单引号
            .configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)
            .registerModule(simpleModule);

    public static <T> T convertObject(Object obj, Class<T> clazz) throws JsonProcessingException {
        String objString = NOT_NULL_INSTANCE.writeValueAsString(obj);
        return DEFAULT_INSTANCE.readValue(objString, clazz);
    }

    public static <T> T convertObject(Object obj, TypeReference<T> valueTypeRef) throws JsonProcessingException {
        String objString = NOT_NULL_INSTANCE.writeValueAsString(obj);
        return DEFAULT_INSTANCE.readValue(objString, valueTypeRef);
    }

}
