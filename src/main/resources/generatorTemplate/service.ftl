package ${basePackage}.service;
import ${basePackage}.model.${modelNameUpperCamel};
import java.util.List;
import ${basePackage}.dto.${dtoNameUpperCamel};
import com.baison.e3plus.common.message.Result;
import com.baison.e3plus.common.message.ResultData;
import com.baison.e3plus.common.message.ResultPageData;
import com.github.pagehelper.PageInfo;

/**
* @author ${author}
* @Date Created in ${date}
* @Description
* @Modified By
*/
public interface ${modelNameUpperCamel}Service{

    void create(${modelNameUpperCamel} ${modelNameLowerCamel});

    Result update(${modelNameUpperCamel} ${modelNameLowerCamel});

    Result delete(Integer id);

    ${modelNameUpperCamel} get(Integer id,String code);

    PageInfo<#noparse><</#noparse>${modelNameUpperCamel}<#noparse>></#noparse> query(${dtoNameUpperCamel} ${dtoNameLowerCamel}, int pageNum, int pageSize);

    void updateBatch(List<Integer> ids, String enable);
}