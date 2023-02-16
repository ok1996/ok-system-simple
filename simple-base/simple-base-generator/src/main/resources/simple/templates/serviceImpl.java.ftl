package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
<#list table.fields as column>
  <#if column.name == "modify_time" && column.columnType == "LOCAL_DATE_TIME">
import java.time.LocalDateTime;
  <#break>
  </#if>
  <#if column.name == "modify_time" && column.columnType == "LOCAL_DATE_TIME">
import java.time.LocalDateTime;
  <#break>
  </#if>
</#list>
/**
 * <p>
 * ${table.comment!} 服务实现类
 * </p>
 *
 * @author ${author}
 */
@Service
public class ${table.serviceImplName} implements ${table.serviceName} {
    @Autowired
    private ${table.mapperName} mapper;

    @Override
    public ${table.entityName} selectById(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public List<${table.entityName}> selectList(${table.entityName} ${table.entityPath}) {
        return mapper.selectList(${table.entityPath});
    }

    @Override
    public int insert(${table.entityName} ${table.entityPath}) {
          <#list table.fields as column>
            <#if column.name == "create_time" && column.columnType == "LOCAL_DATE_TIME">
        ${table.entityPath}.setCreateTime(LocalDateTime.now());
            </#if>
            <#if column.name == "modify_time" && column.columnType == "LOCAL_DATE_TIME">
        ${table.entityPath}.setModifyTime(LocalDateTime.now());
            </#if>
    	  </#list>
        return mapper.insert(${table.entityPath});
    }

    @Override
    public int update(${table.entityName} ${table.entityPath}) {
          <#list table.fields as column>
            <#if column.name == "modify_time" && column.columnType == "LOCAL_DATE_TIME">
        ${table.entityPath}.setModifyTime(LocalDateTime.now());
            </#if>
    	  </#list>
        return mapper.update(${table.entityPath});
    }

    @Override
    public int deleteByIds(Long[] ids) {
        return mapper.deleteByIds(ids);
    }

    @Override
    public int deleteById(Long id) {
        return mapper.deleteById(id);
    }
}
