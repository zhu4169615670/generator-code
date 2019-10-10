package ${basePackage}.service;
import ${basePackage}.model.${modelNameUpperCamel};
import java.util.List;
import ${basePackage}.dto.${dtoNameUpperCamel};
import com.baison.e3plus.common.message.Result;
import com.baison.e3plus.common.message.ResultData;
import com.baison.e3plus.common.message.ResultPageData;

/**
* @ Author     ：${author}
* @ Date       ：Created in ${date}
* @ Description：
* @ Modified By：
*/
public interface ${modelNameUpperCamel}Service{

    Result<ResultObject<#noparse><Object</#noparse>>> create(${modelNameUpperCamel} ${modelNameLowerCamel});

    Result<?> update(${modelNameUpperCamel} ${modelNameLowerCamel});

    Result<?> delete(Integer id);

    Result<ResultData<#noparse><List</#noparse><${modelNameUpperCamel}>>> get(Integer id);

    Result<ResultPageData<#noparse><List</#noparse><${modelNameUpperCamel}>>> query(${dtoNameUpperCamel} ${dtoNameLowerCamel}, int pageNum, int pageSize);
}