package cn.iosd.demo.base.generator;


import cn.iosd.base.generator.api.database.service.DatabaseGenService;
import cn.iosd.base.generator.api.database.vo.DatabaseGenVo;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ok1996
 */
@Slf4j
public class BaseGeneratorApplication {
    public static void main(String[] args) {
        DatabaseGenVo vo = new DatabaseGenVo();
        vo.setAuthorName("ok1996");
        vo.setDataBaseUrl("jdbc:mysql://127.0.0.1:3306/simple_demo?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true");
        vo.setDataBaseUserName("root");
        vo.setDataBasePassword("123456");
        vo.setPackageName("cn.iosd.demo");
        vo.setProjectName("simple-service-generator");
        vo.setModuleName("generator");
        vo.setTableName("demo_article");
        List<String> tablePrefix = new ArrayList<>();
        tablePrefix.add("demo");
        vo.setTablePrefix(tablePrefix);
        DatabaseGenService.generate(vo);
        log.info("文件生成目录：" + new File("target/generator").getAbsolutePath());
    }
}


