package cn.iosd.demo.base.config.init;

import cn.iosd.base.config.api.init.ConfigInit;
import cn.iosd.base.config.api.utils.ConfigUtils;
import cn.iosd.base.config.api.vo.CodeValue;
import cn.iosd.demo.base.config.vo.ClassmateVo;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 初始化数据
 * <p>
 * 创建初始化对象，提供一些常量和方法实现
 *
 * @author ok1996
 */
@Component
public class DemoTestConfigInit {
    /**
     * 一班参数key
     */
    public static final String STUDENT_KEY_ONE = "student-key-test-one";

    /**
     * 二班参数key
     */
    public static final String STUDENT_KEY_TWO = "student-key-test-two";

    @Bean
    public ConfigInit studentTestOneInit() {
        ConfigInit configInit = new ConfigInit(STUDENT_KEY_ONE, "一班同学列表", false, List.of("模块分类1", "分类1-1"));
        configInit.setCodeValues(List.of(
                new CodeValue<Boolean>().setCode(ConfigUtils.OPEN_SIMULATION_CODE).setValue(true),
                new CodeValue<ClassmateVo>().setCode(ConfigUtils.CONTENT_DATA_CODE)
                        .setValue(ClassmateVo.builder().personList(List.of(
                                        ClassmateVo.Person.builder().age(12).name("小库").build(),
                                        ClassmateVo.Person.builder().age(14).name("小明").build()))
                                .build())
        ));
        return configInit;
    }

    @Bean
    public ConfigInit studentTestTwoInit() {
        ConfigInit configInit = new ConfigInit(STUDENT_KEY_TWO, "二班同学列表", false, List.of("模块分类1", "分类1-2"));
        configInit.setCodeValues(List.of(
                new CodeValue<Boolean>().setCode(ConfigUtils.OPEN_SIMULATION_CODE).setValue(true),
                new CodeValue<ClassmateVo>().setCode(ConfigUtils.CONTENT_DATA_CODE)
                        .setValue(ClassmateVo.builder().personList(List.of(
                                        ClassmateVo.Person.builder().age(14).name("小雪").build(),
                                        ClassmateVo.Person.builder().age(15).name("小楚").build()))
                                .build())
        ));
        return configInit;
    }
}
