package ${packageParent}.api.feign;

import ${packageParent}.api.domain.${entity};
import cn.iosd.starter.web.base.CrudOperations;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author ${author}
 */
@FeignClient(name = "${projectName}-service", contextId = "${entity}ServiceFeign", path = "/${projectName}-service/${entity}"
        , url = "${r"$"}{simple.feign.${projectName}-service.url:}", primary = false)
public interface ${entity}Feign extends CrudOperations<${entity}> {

}