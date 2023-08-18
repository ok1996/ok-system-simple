package ${packageParent}.api.feign;

import ${packageParent}.api.domain.${entity};
import cn.iosd.starter.web.base.CrudOperations;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author ${author}
 */
@FeignClient(name = "${projectName}", contextId = "${entity}ServiceFeign", path = "/${projectName}/${entity}"
        , url = "${r"$"}{simple.feign.${projectName}.url:}", primary = false)
public interface ${entity}Feign extends CrudOperations<${entity}> {

}