package cn.iosd.demo.base.param.init;

import cn.iosd.base.param.init.ParamInit;
import cn.iosd.base.param.utils.ParamInitUtil;
import cn.iosd.base.param.vo.CodeValue;
import cn.iosd.demo.base.param.vo.ClassmateVo;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * 初始化数据
 * <p>
 * 创建ParamInit对象，提供一些常量和方法实现
 *
 * @author ok1996
 */
@Component
public class DemoTestParamInit {
    /**
     * 一班参数key
     */
    public static final String STUDENT_KEY_ONE = "student-key-test-one";

    /**
     * 二班参数key
     */
    public static final String STUDENT_KEY_TWO = "student-key-test-two";

    @Bean
    public ParamInit studentTestOneParamInit() {
        ParamInit paramInit = new ParamInit(STUDENT_KEY_ONE, "一班同学列表", false, Arrays.asList("模块分类1", "分类1-1"));
        paramInit.setCodeValues(Arrays.asList(
                new CodeValue<Boolean>().setCode(ParamInitUtil.OPEN_SIMULATION_CODE).setValue(true),
                new CodeValue<ClassmateVo>().setCode(ParamInitUtil.CONTENT_DATA_CODE)
                        .setValue(ClassmateVo.builder().personList(Arrays.asList(
                                        ClassmateVo.Person.builder().age(12).name("小库").build(),
                                        ClassmateVo.Person.builder().age(14).name("小明").build()))
                                .build())
        ));
        return paramInit;
    }

    @Bean
    public ParamInit studentTestTwoParamInit() {
        ParamInit paramInit = new ParamInit(STUDENT_KEY_TWO, "二班同学列表", false, Arrays.asList("模块分类1", "分类1-2"));
        paramInit.setCodeValues(Arrays.asList(
                new CodeValue<Boolean>().setCode(ParamInitUtil.OPEN_SIMULATION_CODE).setValue(true),
                new CodeValue<ClassmateVo>().setCode(ParamInitUtil.CONTENT_DATA_CODE)
                        .setValue(ClassmateVo.builder().personList(Arrays.asList(
                                        ClassmateVo.Person.builder().age(14).name("小雪").build(),
                                        ClassmateVo.Person.builder().age(15).name("小楚").build()))
                                .build())
        ));
        return paramInit;
    }
}
