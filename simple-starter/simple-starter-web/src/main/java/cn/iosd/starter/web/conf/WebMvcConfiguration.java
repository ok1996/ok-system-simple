package cn.iosd.starter.web.conf;


import cn.iosd.starter.web.convert.LocalDateTimeToLongConvert;
import cn.iosd.starter.web.convert.LocalDateToLongConvert;
import cn.iosd.starter.web.convert.LocalTimeToLongConvert;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdDelegatingSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author ok1996
 */
@Configuration
@ConditionalOnProperty(prefix = "simple.jackson.serialize", name = "enabled"
        , havingValue = "true", matchIfMissing = true)
public class WebMvcConfiguration implements WebMvcConfigurer {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(mappingJackson2HttpMessageConverter());
        WebMvcConfigurer.super.configureMessageConverters(converters);
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        SimpleModule simpleModule = new SimpleModule();
        registerSerializers(simpleModule);
        registerDeserializers(simpleModule);
        objectMapper.registerModule(simpleModule);

        jsonConverter.setObjectMapper(objectMapper);
        return jsonConverter;
    }

    private void registerSerializers(SimpleModule simpleModule) {
        simpleModule.addSerializer(LocalDateTime.class, new StdDelegatingSerializer(new LocalDateTimeToLongConvert()));
        simpleModule.addSerializer(LocalDate.class, new StdDelegatingSerializer(new LocalDateToLongConvert()));
        simpleModule.addSerializer(LocalTime.class, new StdDelegatingSerializer(new LocalTimeToLongConvert()));
    }


    private void registerDeserializers(SimpleModule simpleModule) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        simpleModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));
        simpleModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(dateTimeFormatter));
        simpleModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(dateTimeFormatter));
    }



}
