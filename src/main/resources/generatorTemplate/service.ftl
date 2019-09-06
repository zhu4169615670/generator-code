package ${basePackage}.service;
import ${basePackage}.model.${modelNameUpperCamel};

import ${basePackage}.dto.${dtoNameUpperCamel};
import com.baison.e3plus.common.message.Result;

/**
* @ Author     ：${author}
* @ Date       ：Created in ${date}
* @ Description：
* @ Modified By：
*/
public interface ${modelNameUpperCamel}Service{

    Result create(${modelNameUpperCamel} ${modelNameLowerCamel});

    Result update(${modelNameUpperCamel} ${modelNameLowerCamel});

    Result delete(Integer id);

    Result findById(Integer id);

    Result queryPage(${dtoNameUpperCamel} ${dtoNameLowerCamel}, int pageNum, int pageSize);
}