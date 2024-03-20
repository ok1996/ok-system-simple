package ${packageParent}.service.service;

import ${packageParent}.api.domain.${entity};
import ${packageParent}.service.entity.${entity}Entity;
import ${packageParent}.service.mapper.${table.mapperName};
import ${packageParent}.api.service.${table.serviceName};
import cn.iosd.starter.datasource.base.BaseServiceImpl;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * <p>
 * ${table.comment!} 服务实现类-Mybatis
 * </p>
 *
 * @author ${author}
 */
@Service
@Primary
public class ${entity}ServiceImpl extends BaseServiceImpl<${table.mapperName}, ${entity}Entity, ${entity}> implements ${table.serviceName} {

}
