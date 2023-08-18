package ${packageParent}.service.service;

import ${packageParent}.service.entity.${entity}Entity;
import ${packageParent}.service.mapper.${table.mapperName};
import ${packageParent}.api.service.${table.serviceName};
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * ${table.comment!} 服务实现类-Mybatis
 * </p>
 *
 * @author ${author}
 */
@Service
public class ${entity}ServiceImpl extends ServiceImpl<${table.mapperName}, ${entity}Entity> implements ${table.serviceName} {

}
