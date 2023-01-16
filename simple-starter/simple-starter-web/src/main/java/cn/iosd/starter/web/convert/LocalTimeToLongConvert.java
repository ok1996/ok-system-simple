package cn.iosd.starter.web.convert;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;


/**
 * @author ok1996
 */
public class LocalTimeToLongConvert extends StdConverter<LocalTime, Long> {

    @Override
    public Long convert(LocalTime value) {
        return value.atDate(LocalDate.ofEpochDay(0)).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

}
