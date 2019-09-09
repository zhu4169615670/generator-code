<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${basePackage}.dao.${modelNameUpperCamel}Dao">
    <resultMap id="BaseResultMap" type="${basePackage}.model.${modelNameUpperCamel}">
        <id column="id" jdbcType="INTEGER" property="id"/>
        <#list baseDataList as data>
            <#if (data.isPrimaryKey==1)>
                <id column="${data.jdbcColumnName}" jdbcType="${data.jdbcType}" property="${data.columnName}"/>
            </#if>
            <result column="${data.jdbcColumnName}" jdbcType="${data.jdbcType}" property="${data.columnName}"/>
        </#list>
    </resultMap>
    <sql id="Base_Column_List">
        ${colunms}
    </sql>
    <select id="findById" parameterType="java.lang.Integer" resultMap="BaseResultMap">
        select
        <include refid="Base_Column_List"/>
        from ${tablename}
        where id = <#noparse>#{id,jdbcType=INTEGER</#noparse><#noparse>}</#noparse>
    </select>
    <delete id="delete" parameterType="java.lang.Integer">
        delete from ${tablename}
        where id = <#noparse>#{id,jdbcType=INTEGER</#noparse><#noparse>}</#noparse>
    </delete>
    <insert id="create" parameterType="${basePackage}.model.${modelNameUpperCamel}">
        insert into ${tablename}
        <trim prefix="(" suffix=")" suffixOverrides=",">
        <#list baseDataList as data>
            <#if data.jdbcType?index_of("INTEGER")!=-1 || data.jdbcType?index_of("BIGINT")!=-1>
            <if test="${data.columnName} != null">
                ${data.jdbcColumnName}
            </if>
             <#else>
            <if test="${data.columnName} != null and ${data.columnName} != ''">
                ${data.jdbcColumnName}
            </if>
        </#if>
        </#list>
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides=",">
            <#list baseDataList as data>
        <#if data.jdbcType?index_of("INTEGER")!=-1 || data.jdbcType?index_of("BIGINT")!=-1>
            <if test="${data.columnName} != null">
                <#noparse>#{</#noparse>${data.columnName},jdbcType=${data.jdbcType}<#noparse>}</#noparse>
            </if>
        <#else>
            <if test="${data.columnName} != null and ${data.columnName} != ''">
                <#noparse>#{</#noparse>${data.columnName},jdbcType=${data.jdbcType}<#noparse>}</#noparse>
            </if>
        </#if>
            </#list>
        </trim>
    </insert>
    <update id="update" parameterType="${basePackage}.model.${modelNameUpperCamel}">
        update ${tablename}
        <set>
            <#list baseDataList as data>
        <#if data.jdbcType?index_of("INTEGER")!=-1 || data.jdbcType?index_of("BIGINT")!=-1>
            <if test="${data.columnName} != null">
                <#noparse>#{</#noparse>${data.columnName},jdbcType=${data.jdbcType}<#noparse>}</#noparse>,
            </if>
        <#else>
            <if test="${data.columnName} != null and ${data.columnName} != ''">
                <#noparse>#{</#noparse>${data.columnName},jdbcType=${data.jdbcType}<#noparse>}</#noparse>,
            </if>
        </#if>
            </#list>
        </set>
        where id = <#noparse>#{id,jdbcType=INTEGER</#noparse><#noparse>}</#noparse>
    </update>

    <select id="queryPage" resultMap="BaseResultMap"
            parameterType="${basePackage}.dto.${modelNameUpperCamel}QueryDTO">
        select
        <include refid="Base_Column_List"/>
        from
        ${tablename}
        <where>
        <#list baseDataList as data>
        <#if data.jdbcType?index_of("INTEGER")!=-1 || data.jdbcType?index_of("BIGINT")!=-1>
            <if test="${data.columnName} != null">
                and ${data.jdbcColumnName}= <#noparse>#{</#noparse>${data.columnName},jdbcType=${data.jdbcType}<#noparse>}</#noparse>
            </if>
        <#else>
            <if test="${data.columnName} != null and ${data.columnName} != ''">
                and ${data.jdbcColumnName}= <#noparse>#{</#noparse>${data.columnName},jdbcType=${data.jdbcType}<#noparse>}</#noparse>
            </if>
        </#if>
        </#list>
        </where>
    </select>
</mapper>