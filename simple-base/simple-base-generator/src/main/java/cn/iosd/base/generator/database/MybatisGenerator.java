package cn.iosd.base.generator.database;

import cn.iosd.base.generator.vo.MybatisGeneratorVo;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 代码生成器
 * <p>依赖使用：[simple-starter-web][simple-starter-datasource]</p>
 *
 * @author ok1996
 */
public class MybatisGenerator {
    /**
     * 文件输出地址前缀
     */
    private static final String OUTPUT_DIR_PREFIX = "./target/generator/src/main/java";
    /**
     * 模版位置
     */
    private static final String TEMPLATES_DIR_TEMPLATES = "/simple/templates/";
    private static final String TEMPLATES_DIR_CONTROLLER = TEMPLATES_DIR_TEMPLATES + "controller.java";
    private static final String TEMPLATES_DIR_ENTITY = TEMPLATES_DIR_TEMPLATES + "entity.java";
    private static final String TEMPLATES_DIR_MAPPER_JAVA = TEMPLATES_DIR_TEMPLATES + "mapper.java";
    private static final String TEMPLATES_DIR_MAPPER_XML = TEMPLATES_DIR_TEMPLATES + "mapper.xml";
    private static final String TEMPLATES_DIR_SERVICE = TEMPLATES_DIR_TEMPLATES + "service.java";
    private static final String TEMPLATES_DIR_SERVICE_IMPL = TEMPLATES_DIR_TEMPLATES + "serviceImpl.java";
    private static final String TEMPLATES_DIR_CONFIG = TEMPLATES_DIR_TEMPLATES + "config.java.ftl";


    @SneakyThrows
    public static void generate(MybatisGeneratorVo vo) {
        //文件输出地址前缀-根据系统变化
        String outputDirPrefixReal = OUTPUT_DIR_PREFIX.replace("/", File.separator);
        StringBuffer outputDirModule = new StringBuffer(outputDirPrefixReal)
                .append(File.separator).append(vo.getPackageName().replace(".", File.separator))
                .append(File.separator).append(vo.getModuleName()).append(File.separator);
        //xml文件输出地址-根据系统变化
        String outputDirXml = outputDirModule + "mapper" + File.separator + "xml";
        //config文件输出地址-根据系统变化
        StringBuffer customFileConf = new StringBuffer(File.separator).append("..").append(File.separator).append("config")
                .append(File.separator).append(StringUtils.capitalize(vo.getModuleName())).append("AutoConfig.java");
        //定制模版传参
        Map<String, Object> customMap = new HashMap<>(16);
        StringBuffer packageParent = new StringBuffer(vo.getPackageName()).append(".").append(vo.getModuleName()).append(".");
        String packageConfig = packageParent + "config";
        //配置文件包名
        customMap.put("packageConfig", packageConfig);
        //模块名称-首字母大写
        customMap.put("ModuleName", StringUtils.capitalize(vo.getModuleName()));

        FastAutoGenerator.create(vo.getDataBaseUrl(), vo.getDataBaseUserName(), vo.getDataBasePassword())
                .globalConfig(builder -> {
                    builder.author(vo.getAuthorName())
                            .enableSpringdoc()
                            .outputDir(outputDirPrefixReal)
                            .disableOpenDir();
                })
                .packageConfig(builder -> {
                    builder.parent(vo.getPackageName())
                            .moduleName(vo.getModuleName())
                            .xml("mapper")
                            .pathInfo(Collections.singletonMap(OutputFile.xml, outputDirXml));
                })
                .strategyConfig(builder -> {
                    builder.addInclude(vo.getTableName());
                    if (vo.getTablePrefix() != null) {
                        builder.addTablePrefix(vo.getTablePrefix());
                    }
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .templateConfig(builder -> {
                    builder.controller(TEMPLATES_DIR_CONTROLLER);
                    builder.mapper(TEMPLATES_DIR_MAPPER_JAVA);
                    builder.xml(TEMPLATES_DIR_MAPPER_XML);
                    builder.service(TEMPLATES_DIR_SERVICE);
                    builder.serviceImpl(TEMPLATES_DIR_SERVICE_IMPL);
                    builder.entity(TEMPLATES_DIR_ENTITY);
                })
                .injectionConfig(builder -> {
                    builder.customMap(customMap);

                    Map<String, String> customFile = new HashMap<>(16);
                    customFile.put(customFileConf.toString(), TEMPLATES_DIR_CONFIG);
                    builder.customFile(customFile);
                })
                .execute();
    }

}
