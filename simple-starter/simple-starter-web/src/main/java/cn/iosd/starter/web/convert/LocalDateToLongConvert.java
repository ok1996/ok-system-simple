package cn.iosd.starter.web.convert;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.time.LocalDate;
import java.time.ZoneId;

/**
 * @author ok1996
 */
public class LocalDateToLongConvert extends StdConverter<LocalDate, Long> {

    @Override
    public Long convert(LocalDate value) {
        return value.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

}
