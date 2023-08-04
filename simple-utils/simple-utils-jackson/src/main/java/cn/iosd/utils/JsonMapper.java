package cn.iosd.utils;

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
 * 提供 Json 序列化和反序列化功能的工具类。
 * <p>
 * 注：非线程安全，使用时需要注意多线程环境下的并发访问问题。
 *
 * @author ok1996
 */
public class JsonMapper {

    /**
     * ObjectMapper 对象，用于实现 Json 的序列化和反序列化。
     * <p>
     * 本类使用 ObjectMapper 对象实现 Json 的序列化和反序列化操作。
     * 默认情况下，本类将 null 属性排除在 Json 序列化结果之外，不会在反序列化时抛出异常，
     * 并且会允许空对象进行序列化操作。同时，本类还支持将 BigDecimal 对象序列化为普通数字，
     * 并且支持在 Json 字段名中使用单引号和不使用引号。
     */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            .configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true)
            .configure(JsonParser.Feature.ALLOW_COMMENTS, true)
            .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
            .configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)
            .registerModule(new SimpleModule());

    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

    /**
     * 将对象转换为指定类型的对象
     *
     * @param obj   要转换的对象
     * @param clazz 目标类型的Class对象
     * @param <T>   转换后的对象类型
     * @return 转换后的对象
     */
    public static <T> T convertObject(Object obj, Class<T> clazz) {
        return OBJECT_MAPPER.convertValue(obj, clazz);
    }

    /**
     * 将对象转换为指定类型的对象
     *
     * @param obj          要转换的对象
     * @param valueTypeRef 目标类型的TypeReference对象
     * @param <T>          转换后的对象类型
     * @return 转换后的对象
     */
    public static <T> T convertObject(Object obj, TypeReference<T> valueTypeRef) {
        return OBJECT_MAPPER.convertValue(obj, valueTypeRef);
    }

    /**
     * 将JSON字符串反序列化为指定类的Java对象。
     *
     * @param obj   要反序列化的JSON字符串。
     * @param clazz 表示要创建的Java对象类型的类。
     * @param <T>   要创建的Java对象的类型。
     * @return 表示反序列化JSON数据的Java对象。
     * @throws JsonProcessingException 如果在反序列化过程中出现错误。
     */
    public static <T> T readValue(String obj, Class<T> clazz) throws JsonProcessingException {
        return OBJECT_MAPPER.readValue(obj, clazz);
    }

    /**
     * 将JSON字符串反序列化为指定TypeReference表示的Java对象。
     *
     * @param obj         要反序列化的JSON字符串。
     * @param valueTypeRef 表示要创建的Java对象类型的TypeReference。
     * @param <T>         要创建的Java对象的类型。
     * @return 表示反序列化JSON数据的Java对象。
     * @throws JsonProcessingException 在反序列化过程中出现错误。
     */
    public static <T> T readValue(String obj, TypeReference<T> valueTypeRef) throws JsonProcessingException {
        return OBJECT_MAPPER.readValue(obj, valueTypeRef);
    }

}
