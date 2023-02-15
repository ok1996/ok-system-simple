package cn.iosd.demo.base.generator;


import cn.iosd.base.generator.database.MybatisGenerator;
import cn.iosd.base.generator.vo.MybatisGeneratorVo;

/**
 * @author ok1996
 */
public class BaseGeneratorApplication {
    public static void main(String[] args) {
        MybatisGeneratorVo vo = new MybatisGeneratorVo();
        vo.setAuthorName("ok1996");
        vo.setDataBaseUrl("jdbc:mysql://127.0.0.1:3306/generator?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true");
        vo.setDataBaseUserName("root");
        vo.setDataBasePassword("123456");
        vo.setPackageName("cn.iosd.demo");
        vo.setModuleName("param");
        vo.setTableName("base_param");
        MybatisGenerator.generate(vo);
    }
}


