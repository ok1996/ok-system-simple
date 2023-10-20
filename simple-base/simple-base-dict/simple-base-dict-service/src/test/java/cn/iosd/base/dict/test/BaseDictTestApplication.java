package cn.iosd.base.dict.test;

import cn.iosd.base.generator.api.utils.DatabaseGenUtils;
import cn.iosd.base.generator.api.vo.DatabaseGenVo;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Collections;

/**
 * @author ok1996
 */
@Slf4j
public class BaseDictTestApplication {
    public static void main(String[] args) {
        DatabaseGenVo vo = new DatabaseGenVo();
        vo.setAuthorName("ok1996");
        vo.setDataBaseUrl("jdbc:mysql://127.0.0.1:3306/simple_demo?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8");
        vo.setDataBaseUserName("root");
        vo.setDataBasePassword("123456");
        vo.setPackageName("cn.iosd.base");
        vo.setProjectName("simple-base-dict");
        vo.setModuleName("dict");
        vo.setTableNames(Collections.singletonList("base_dict_info"));
        vo.setTablePrefix(Collections.singletonList("base"));
        DatabaseGenUtils.generate(vo);
        log.info("文件生成目录：" + new File("target/generator").getAbsolutePath());
    }
}
