package ${basePackage}.dao;
import ${basePackage}.model.${modelNameUpperCamel};

import ${basePackage}.dto.${dtoNameUpperCamel};
import com.baison.e3plus.common.message.Result;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import com.github.pagehelper.Page;

/**
* @ Author     ：${author}
* @ Date       ：Created in ${date}
* @ Description：
* @ Modified By：
*/
@Mapper
public interface ${modelNameUpperCamel}Dao{

    int create(${modelNameUpperCamel} ${modelNameLowerCamel});

    int update(${modelNameUpperCamel} ${modelNameLowerCamel});

    int delete(@Param("id") Integer id);

    ${modelNameUpperCamel} findById(@Param("id") Integer id);

    Page<${modelNameUpperCamel}> queryPage(${dtoNameUpperCamel} ${dtoNameLowerCamel});
}