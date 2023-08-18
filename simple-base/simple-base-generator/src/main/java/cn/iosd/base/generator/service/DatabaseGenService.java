package cn.iosd.base.generator.service;

import cn.iosd.base.generator.vo.DatabaseGenVo;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.builder.CustomFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 代码生成器
 *
 * @author ok1996
 */
@Service
public class DatabaseGenService {
    /**
     * 文件输出地址前缀
     */
    private static final String OUTPUT_DIR_PREFIX = "./target/generator";

    /**
     * Api模块模版位置
     */
    private static final String TEMPLATES_DIR_TEMPLATES_API = "/templates/api/";

    /**
     * Service模块模版位置
     */
    private static final String TEMPLATES_DIR_TEMPLATES_SERVICE = "/templates/service/";

    /**
     * 实体类
     */
    private static final String TEMPLATES_DIR_TEMPLATES_API_DOMAIN = TEMPLATES_DIR_TEMPLATES_API + "domain.java";
    /**
     * Feign接口
     */
    private static final String TEMPLATES_DIR_TEMPLATES_API_FEIGN = TEMPLATES_DIR_TEMPLATES_API + "feign.java.ftl";
    /**
     * 服务类接口
     */
    private static final String TEMPLATES_DIR_TEMPLATES_API_SERVICE = TEMPLATES_DIR_TEMPLATES_API + "service.java";
    /**
     * 服务类实现-Feign
     */
    private static final String TEMPLATES_DIR_TEMPLATES_API_SERVICE_IMPL = TEMPLATES_DIR_TEMPLATES_API + "serviceImpl.java.ftl";

    /**
     * 前端控制器
     */
    private static final String TEMPLATES_DIR_TEMPLATES_SERVICE_CONTROLLER = TEMPLATES_DIR_TEMPLATES_SERVICE + "controller.java";
    /**
     * Mapper 接口
     */
    private static final String TEMPLATES_DIR_TEMPLATES_SERVICE_MAPPER_JAVA = TEMPLATES_DIR_TEMPLATES_SERVICE + "mapper.java";
    /**
     * Mapper 实现
     */
    private static final String TEMPLATES_DIR_TEMPLATES_SERVICE_MAPPER_XML = TEMPLATES_DIR_TEMPLATES_SERVICE + "mapper.xml";

    /**
     * 服务类实现-Mybatis
     */
    private static final String TEMPLATES_DIR_TEMPLATES_SERVICE_SERVICE_IMPL = TEMPLATES_DIR_TEMPLATES_SERVICE + "serviceImpl.java";

    /**
     * 实体类
     */
    private static final String TEMPLATES_DIR_TEMPLATES_SERVICE_ENTITY = TEMPLATES_DIR_TEMPLATES_SERVICE + "entity.java.ftl";

    public static void generate(DatabaseGenVo vo) {
        String moduleName = vo.getModuleName();
        String packageName = vo.getPackageName();

        // 文件输出地址前缀
        String outputDirPrefixReal = OUTPUT_DIR_PREFIX.replace("/", File.separator);
        String outputDirModule = String.join(File.separator, outputDirPrefixReal, vo.getPackageName().replace(".", File.separator), vo.getModuleName()) + File.separator;
        String outputDirModuleApi = outputDirModule + "api" + File.separator;
        String outputDirModuleService = outputDirModule + "service" + File.separator;

        String outputDirModuleApiEntity = outputDirModuleApi + "domain";
        String outputDirModuleApiService = outputDirModuleApi + "service";

        String outputDirModuleApiFeign = outputDirModuleApi + "feign";
        String outputDirModuleApiServiceImpl = outputDirModuleApi + "service" + File.separator + "impl";

        String outputDirModuleServiceController = outputDirModuleService + "controller";
        String outputDirModuleServiceMapper = outputDirModuleService + "mapper";
        String outputDirModuleServiceMapperXml = outputDirModuleService + "mapper" + File.separator + "xml";
        String outputDirModuleServiceService = outputDirModuleService + "service";
        String outputDirModuleServiceEntity = outputDirModuleService + "entity";
        // 基础模板文件输出地址
        Map<OutputFile, String> pathInfo = new HashMap<>(16);
        pathInfo.put(OutputFile.xml, outputDirModuleServiceMapperXml);
        pathInfo.put(OutputFile.controller, outputDirModuleServiceController);
        pathInfo.put(OutputFile.entity, outputDirModuleApiEntity);
        pathInfo.put(OutputFile.mapper, outputDirModuleServiceMapper);
        pathInfo.put(OutputFile.service, outputDirModuleApiService);
        pathInfo.put(OutputFile.serviceImpl, outputDirModuleServiceService);


        // 定制模版传参
        String packageParent = packageName + "." + moduleName;
        Map<String, Object> customMap = new HashMap<>(3);
        customMap.put("packageParent", packageParent);
        customMap.put("projectName", vo.getProjectName());


        List<CustomFile> customFiles = new ArrayList<>();
        customFiles.add(new CustomFile.Builder().fileName("Feign.java")
                .templatePath(TEMPLATES_DIR_TEMPLATES_API_FEIGN).filePath(outputDirModuleApiFeign).enableFileOverride().build());
        customFiles.add(new CustomFile.Builder().fileName("ServiceFeign.java")
                .templatePath(TEMPLATES_DIR_TEMPLATES_API_SERVICE_IMPL).filePath(outputDirModuleApiServiceImpl).enableFileOverride().build());
        customFiles.add(new CustomFile.Builder().fileName("Entity.java")
                .templatePath(TEMPLATES_DIR_TEMPLATES_SERVICE_ENTITY).filePath(outputDirModuleServiceEntity).enableFileOverride().build());

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
                    builder.addInclude(vo.getTableNames());
                    if (vo.getTablePrefix() != null) {
                        builder.addTablePrefix(vo.getTablePrefix());
                    }
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .templateConfig(builder -> {
                    builder.controller(TEMPLATES_DIR_TEMPLATES_SERVICE_CONTROLLER);
                    builder.mapper(TEMPLATES_DIR_TEMPLATES_SERVICE_MAPPER_JAVA);
                    builder.xml(TEMPLATES_DIR_TEMPLATES_SERVICE_MAPPER_XML);
                    builder.service(TEMPLATES_DIR_TEMPLATES_API_SERVICE);
                    builder.serviceImpl(TEMPLATES_DIR_TEMPLATES_SERVICE_SERVICE_IMPL);
                    builder.entity(TEMPLATES_DIR_TEMPLATES_API_DOMAIN);
                })
                .injectionConfig(builder -> {
                    builder.customMap(customMap);
                    builder.customFile(customFiles);
                }).execute();
    }
}
