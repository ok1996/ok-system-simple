package cn.iosd.starter.encode.rsa.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * 提供 Json 序列化和反序列化功能的工具类。
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
     * <p>
     * 注：使用ThreadLocal来保存ObjectMapper对象，确保每个线程都使用自己的ObjectMapper对象
     */
    private static final ThreadLocal<ObjectMapper> OBJECT_MAPPER_THREAD_LOCAL = ThreadLocal.withInitial(() ->
            new ObjectMapper()
                    .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                    .configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true)
                    .configure(JsonParser.Feature.ALLOW_COMMENTS, true)
                    .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
                    .configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)
                    .registerModule(new SimpleModule()));

    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER_THREAD_LOCAL.get();
    }

}


