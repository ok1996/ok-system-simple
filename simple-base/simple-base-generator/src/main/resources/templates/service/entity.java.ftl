package ${packageParent}.service.entity;

import ${packageParent}.api.domain.${entity};
<#if table.convert>
import com.baomidou.mybatisplus.annotation.TableName;
</#if>


/**
 * @author ${author}
 */
<#if table.convert>
@TableName("${schemaName}${table.name}")
</#if>
public class ${entity}Entity extends ${entity}{

}
