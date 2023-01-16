package cn.iosd.starter.web.convert;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author ok1996
 */
public class LocalDateTimeToLongConvert extends StdConverter<LocalDateTime, Long> {

    @Override
    public Long convert(LocalDateTime value) {
        return value.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
