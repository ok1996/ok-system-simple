package cn.iosd.base.generator.database;

import cn.iosd.base.generator.vo.MybatisGeneratorVo;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 代码生成器
 *
 * @author ok1996
 */
public class MybatisGenerator {
    /**
     * 依赖模块版本号
     */
    private static final String SIMPLE_VERSION = "2023.2.2.1";

    /**
     * 文件输出地址前缀
     */
    private static final String OUTPUT_DIR_PREFIX = "./target/generator";

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

    private static final String TEMPLATES_DIR_YML = TEMPLATES_DIR_TEMPLATES + "application.yml.ftl";
    private static final String TEMPLATES_DIR_CONFIG_YML = TEMPLATES_DIR_TEMPLATES + "configApplication.yml.ftl";
    private static final String TEMPLATES_DIR_POM = TEMPLATES_DIR_TEMPLATES + "pom.xml.ftl";
    private static final String TEMPLATES_DIR_APPLICATION = TEMPLATES_DIR_TEMPLATES + "application.java.ftl";

    /**
     * 自定义文件输出地址前缀
     */
    private static final String CUSTOM_FILE_PREFIX = File.separator + ".." + File.separator;

    /**
     * 代码生成函数
     * <p/> 注：依赖使用mybatis-plus、若仅使用Mybatis注意配置项修改mybatis.mapperLocations
     *
     * @param vo
     */
    @SneakyThrows
    public static void generate(MybatisGeneratorVo vo) {
        //文件输出地址前缀
        String outputDirPrefixReal = OUTPUT_DIR_PREFIX.replace("/", File.separator);
        StringBuffer outputDirModule = new StringBuffer(outputDirPrefixReal).append(File.separator).append(vo.getPackageName().replace(".", File.separator)).append(File.separator).append(vo.getModuleName()).append(File.separator);

        //基础模板文件输出地址
        String outputDirXml = outputDirModule + "mapper" + File.separator + "xml";
        String outputDirController = outputDirModule + "controller";
        String outputDirMapper = outputDirModule + "mapper";
        String outputDirService = outputDirModule + "service";
        String outputDirServiceImpl = outputDirModule + "service" + File.separator + "impl";
        String outputDirEntity = outputDirModule + "entity";

        Map<OutputFile, String> pathInfo = new HashMap<>(16);
        pathInfo.put(OutputFile.xml, outputDirXml);
        pathInfo.put(OutputFile.controller, outputDirController);
        pathInfo.put(OutputFile.entity, outputDirEntity);
        pathInfo.put(OutputFile.mapper, outputDirMapper);
        pathInfo.put(OutputFile.service, outputDirService);
        pathInfo.put(OutputFile.serviceImpl, outputDirServiceImpl);

        //自定义模板文件输出地址
        StringBuffer customFileConf = new StringBuffer(CUSTOM_FILE_PREFIX).append("config").append(File.separator).append(StringUtils.capitalize(vo.getModuleName()))
                .append("AutoConfig.java");
        StringBuffer customFileYml = new StringBuffer(CUSTOM_FILE_PREFIX).append("resources").append(File.separator)
                .append("application.yml");
        StringBuffer customFileConfigYml = new StringBuffer(CUSTOM_FILE_PREFIX).append("resources").append(File.separator).append("config").append(File.separator)
                .append("application.yml");
        StringBuffer customFileApplication = new StringBuffer(CUSTOM_FILE_PREFIX).append(StringUtils.capitalize(vo.getModuleName()))
                .append("Application.java");
        StringBuffer customFilePom = new StringBuffer(CUSTOM_FILE_PREFIX).append("pom.xml");

        Map<String, String> customFile = new HashMap<>(16);
        customFile.put(customFileConf.toString(), TEMPLATES_DIR_CONFIG);
        customFile.put(customFileYml.toString(), TEMPLATES_DIR_YML);
        customFile.put(customFileConfigYml.toString(), TEMPLATES_DIR_CONFIG_YML);
        customFile.put(customFilePom.toString(), TEMPLATES_DIR_POM);
        customFile.put(customFileApplication.toString(), TEMPLATES_DIR_APPLICATION);
        customFile.put(customFilePom.toString(), TEMPLATES_DIR_POM);
        //定制模版传参
        Map<String, Object> customMap = new HashMap<>(16);
        StringBuffer packageParent = new StringBuffer(vo.getPackageName()).append(".").append(vo.getModuleName());
        customMap.put("packageParent", packageParent.toString());
        packageParent.append(".").append("config");
        customMap.put("packageConfig", packageParent.toString());
        customMap.put("ModuleName", StringUtils.capitalize(vo.getModuleName()));
        StringBuffer mapperLocations = new StringBuffer("/")
                .append(vo.getPackageName().replace(".", "/")).append("/").append(vo.getModuleName()).append("/");
        customMap.put("mapperLocations", mapperLocations);
        customMap.put("simpleVersion", SIMPLE_VERSION);
        customMap.put("projectName", vo.getProjectName());

        FastAutoGenerator.create(vo.getDataBaseUrl(), vo.getDataBaseUserName(), vo.getDataBasePassword())
                .globalConfig(builder -> builder.author(vo.getAuthorName()).enableSpringdoc().outputDir(outputDirPrefixReal).disableOpenDir())
                .packageConfig(builder -> builder.parent(vo.getPackageName()).moduleName(vo.getModuleName()).xml("mapper").pathInfo(pathInfo))
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
                    builder.customFile(customFile);
                }).execute();
    }

}
