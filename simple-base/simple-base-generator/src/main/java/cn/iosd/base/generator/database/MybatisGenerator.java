package cn.iosd.base.generator.database;

import cn.iosd.base.generator.vo.MybatisGeneratorVo;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
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
    private static final String SIMPLE_VERSION = "2022.3.4.0-SNAPSHOT";

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
    private static final String CUSTOM_FILE_PREFIX = "Config"+File.separator;

    /**
     * 代码生成函数
     * <p/> 注：依赖使用mybatis-plus、若仅使用Mybatis注意配置项修改mybatis.mapperLocations
     *
     * @param vo
     */
    public static void generate(MybatisGeneratorVo vo) {
        // 文件输出地址前缀
        String outputDirPrefixReal = OUTPUT_DIR_PREFIX.replace("/", File.separator);
        String outputDirModule = String.join(File.separator, outputDirPrefixReal, vo.getPackageName().replace(".", File.separator), vo.getModuleName()) + File.separator;

        // 基础模板文件输出地址
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

        // 自定义模板文件输出地址
        String customFilePrefix = CUSTOM_FILE_PREFIX;
        String moduleName = vo.getModuleName();
        String packageName = vo.getPackageName();
        String capitalizedModuleName = StringUtils.capitalize(moduleName);
        String packageParent = packageName + "." + moduleName;
        String configPackage = packageParent + ".config";
        String mapperLocations = "/" + packageName.replace(".", "/") + "/" + moduleName + "/";

        Map<String, String> customFile = new HashMap<>();
        customFile.put(customFilePrefix + "config/" + capitalizedModuleName + "AutoConfig.java", TEMPLATES_DIR_CONFIG);
        customFile.put(customFilePrefix + "resources/application.yml", TEMPLATES_DIR_YML);
        customFile.put(customFilePrefix + "resources/config/application.yml", TEMPLATES_DIR_CONFIG_YML);
        customFile.put(customFilePrefix + "pom.xml", TEMPLATES_DIR_POM);
        customFile.put(customFilePrefix + capitalizedModuleName + "Application.java", TEMPLATES_DIR_APPLICATION);


        // 定制模版传参
        Map<String, Object> customMap = new HashMap<>(16);
        customMap.put("packageParent", packageParent);
        customMap.put("packageConfig", configPackage);
        customMap.put("ModuleName", capitalizedModuleName);
        customMap.put("mapperLocations", mapperLocations);
        customMap.put("simpleVersion", SIMPLE_VERSION);
        customMap.put("projectName", vo.getProjectName());

        FastAutoGenerator.create(vo.getDataBaseUrl(), vo.getDataBaseUserName(), vo.getDataBasePassword())
                .globalConfig(builder -> builder
                        .author(vo.getAuthorName())
                        .enableSpringdoc()
                        .outputDir(outputDirPrefixReal)
                        .disableOpenDir()
                )
                .packageConfig(builder -> builder
                        .parent(packageName)
                        .moduleName(moduleName)
                        .xml("mapper")
                        .pathInfo(pathInfo)
                )
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
