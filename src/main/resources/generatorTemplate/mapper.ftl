<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${basePackage}.dao.${modelNameUpperCamel}Dao">
    <resultMap id="BaseResultMap" type="${basePackage}.model.${modelNameUpperCamel}">
        <#list baseDataList as data>
            <#if (data.isPrimaryKey==1)>
                <id column="${data.jdbcColumnName}" jdbcType="${data.jdbcType}" property="${data.columnName}"/>
            <#else>
                <result column="${data.jdbcColumnName}" jdbcType="${data.jdbcType}" property="${data.columnName}"/>
            </#if>
        </#list>
    </resultMap>
    <sql id="Base_Column_List">
        ${colunms}
    </sql>
    <select id="get" parameterType="java.lang.Integer" resultMap="BaseResultMap">
        select
        <include refid="Base_Column_List"/>
        from ${tablename}
        where 1=1
        <if test="id != null">
            and id = <#noparse>#{</#noparse>id,<#noparse>}</#noparse>
        </if>
        <if test="code != null and code !=''">
            and code = <#noparse>#{</#noparse>code,<#noparse>}</#noparse>
        </if>
    </select>
    <delete id="delete" parameterType="java.lang.Integer">
        delete from ${tablename}
        where id = <#noparse>#{id,jdbcType=INTEGER</#noparse><#noparse>}</#noparse>
    </delete>
    <insert id="create" parameterType="${basePackage}.model.${modelNameUpperCamel}"  useGeneratedKeys="true" keyProperty="id" keyColumn="id">
        insert into ${tablename}
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <#list baseDataList as data>
                <#if data.jdbcType?index_of("INTEGER")!=-1||data.jdbcType?index_of("TIMESTAMP")!=-1||data.jdbcType?index_of("BIGINT")!=-1>
                    <if test="${data.columnName} != null">
                        ${data.jdbcColumnName},
                    </if>
                <#else>
                    <if test="${data.columnName} != null and ${data.columnName} != ''">
                        ${data.jdbcColumnName},
                    </if>
                </#if>
            </#list>
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides=",">
            <#list baseDataList as data>
                <#if data.jdbcType?index_of("INTEGER")!=-1||data.jdbcType?index_of("BIGINT")!=-1||data.jdbcType?index_of("TIMESTAMP")!=-1>
                    <if test="${data.columnName} != null">
                        <#noparse>#{</#noparse>${data.columnName},jdbcType=${data.jdbcType}<#noparse>}</#noparse>,
                    </if>
                <#else>
                    <if test="${data.columnName} != null and ${data.columnName} != ''">
                        <#noparse>#{</#noparse>${data.columnName},jdbcType=${data.jdbcType}<#noparse>}</#noparse>,
                    </if>
                </#if>
            </#list>
        </trim>
    </insert>
    <update id="update" parameterType="${basePackage}.model.${modelNameUpperCamel}">
        update ${tablename}
        <set>
            <#list baseDataList as data>
                <#if data.jdbcType?index_of("INTEGER")!=-1||data.jdbcType?index_of("BIGINT")!=-1||data.jdbcType?index_of("TIMESTAMP")!=-1>
                    <if test="${data.columnName} != null">
                        ${data.jdbcColumnName} = <#noparse>#{</#noparse>${data.columnName},jdbcType=${data.jdbcType}<#noparse>}</#noparse>,
                    </if>
                <#else>
                    <if test="${data.columnName} != null and ${data.columnName} != ''">
                        ${data.jdbcColumnName} = <#noparse>#{</#noparse>${data.columnName},jdbcType=${data.jdbcType}<#noparse>}</#noparse>,
                    </if>
                </#if>
            </#list>
        </set>
        where id = <#noparse>#{id,jdbcType=INTEGER</#noparse><#noparse>}</#noparse>
    </update>

    <select id="query" resultMap="BaseResultMap"
            parameterType="${basePackage}.dto.${modelNameUpperCamel}QueryDTO">
        select
        <include refid="Base_Column_List"/>
        from
        ${tablename}
        <where>
            <#list baseDataList as data>
                <#if data.jdbcType?index_of("INTEGER")!=-1||data.jdbcType?index_of("BIGINT")!=-1||data.jdbcType?index_of("TIMESTAMP")!=-1>
                    <if test="${data.columnName} != null">
                        and ${data.jdbcColumnName} like CONCAT('%', <#noparse>#{</#noparse>${data.columnName},jdbcType=${data.jdbcType}<#noparse>}</#noparse>, '%')
                    </if>
                <#else>
                    <if test="${data.columnName} != null and ${data.columnName} != ''">
                        and ${data.jdbcColumnName} like CONCAT('%', <#noparse>#{</#noparse>${data.columnName},jdbcType=${data.jdbcType}<#noparse>}</#noparse>, '%')
                    </if>
                </#if>
            </#list>
        </where>
    </select>

    <select id="findByCode" resultType="java.lang.Integer">
        select count(1) from ${tablename} where code =<#noparse>#{</#noparse>code<#noparse>}</#noparse>
    </select>

    <update id="updateBatch">
        update ${tablename} set status = <#noparse>#{</#noparse>status<#noparse>}</#noparse>
        where id in
        <foreach item="item" index="index" collection="ids" open="("
                 separator="," close=")">
            <#noparse>#{</#noparse>item<#noparse>}</#noparse>
        </foreach>
    </update>
</mapper>