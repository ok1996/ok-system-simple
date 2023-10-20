package ${packageParent}.service.controller;

import ${packageParent}.service.entity.${table.entityName}Entity;
import cn.iosd.starter.datasource.base.BaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 */
@Tag(name = "${table.comment!}")
@RestController
@RequestMapping("/${projectName}-service/${entity}")
public class ${table.controllerName} extends BaseController<${table.entityName}Entity>{

}
