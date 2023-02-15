<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${package.Mapper}.${table.mapperName}">

    <!-- 通用查询映射结果 -->
    <resultMap id="BaseResultMap" type="${package.Entity}.${entity}">
<#list table.fields as field>
<#if field.keyFlag><#--生成主键排在第一位-->
        <id column="${field.name}" property="${field.propertyName}" />
</#if>
</#list>
<#list table.commonFields as field><#--生成公共字段 -->
        <result column="${field.name}" property="${field.propertyName}" />
</#list>
<#list table.fields as field>
<#if !field.keyFlag><#--生成普通字段 -->
        <result column="${field.name}" property="${field.propertyName}" />
</#if>
</#list>
    </resultMap>

    <!-- 通用查询结果列 -->
    <sql id="Base_Column_List">
        ${table.fieldNames}
    </sql>

	<sql id="Base_Column_Where">
	    <#list table.fields as column>
	       <if test="${column.propertyName}!=null">
			  and ${column.name} = ${"#{"}${column.propertyName}${"}"}
		   </if>
	    </#list>
	</sql>

    <select id="selectList" parameterType="${package.Entity}.${entity}" resultMap="BaseResultMap">
        select
        <include refid="Base_Column_List"/>
        from ${table.name}
        <where>
            <include refid="Base_Column_Where" />
        </where>
    </select>

    <select id="selectById" parameterType="Long" resultMap="BaseResultMap">
        select
        <include refid="Base_Column_List"/>
        from ${table.name}
        where id = ${"#{"}id${"}"}
    </select>

    <insert id="insert" parameterType="${package.Entity}.${entity}">
        insert into ${table.name}
        <trim prefix="(" suffix=")" suffixOverrides=",">
          <#list table.fields as column>
            <#if column.propertyType != "String">
            <if test="${column.propertyName}!=null">${column.name},</if>
            <#else>
            <if test="${column.propertyName}!=null and ${column.propertyName}!=''">${column.name},</if>
            </#if>
          </#list>
         </trim>
        <trim prefix="values (" suffix=")" suffixOverrides=",">
          <#list table.fields as column>
            <#if column.propertyType != "String">
            <if test="${column.propertyName}!=null">${"#{"}${column.propertyName}${"}"},</if>
            <#else>
            <if test="${column.propertyName}!=null and ${column.propertyName}!=''">${"#{"}${column.propertyName}${"}"},</if>
            </#if>
          </#list>
         </trim>
    </insert>

    <update id="update" parameterType="${package.Entity}.${entity}">
        update ${table.name}
        <trim prefix="SET" suffixOverrides=",">
          <#list table.fields as column>
            <#if column.propertyType != "String">
            <if test="${column.propertyName}!=null">${column.name} = ${"#{"}${column.propertyName}${"}"},</if>
            <#else>
            <if test="${column.propertyName}!=null and ${column.propertyName}!=''">${column.name} = ${"#{"}${column.propertyName}${"}"},</if>
            </#if>
          </#list>
        </trim>
        where id = ${"#{"}id}
    </update>

    <delete id="deleteById" parameterType="Long">
        delete from ${table.name} where id = ${"#{"}id}
    </delete>

    <delete id="deleteByIds" parameterType="Long">
        delete from ${table.name} where id in
        <foreach item="id" collection="array" open="(" separator="," close=")">
            ${"#{"}id}
        </foreach>
    </delete>
</mapper>
