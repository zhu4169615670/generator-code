package ${basePackage}.dao;
import ${basePackage}.model.${modelNameUpperCamel};

import ${basePackage}.dto.${dtoNameUpperCamel};
import com.baison.e3plus.common.message.Result;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import com.github.pagehelper.Page;
import java.util.List;

/**
* @Author:${author}
* @Date:Created in ${date}
* @Description:
* @Modified By:
*/
public interface ${modelNameUpperCamel}Dao{

    int create(${modelNameUpperCamel} ${modelNameLowerCamel});

    int update(${modelNameUpperCamel} ${modelNameLowerCamel});

    int delete(@Param("id") Integer id);

    ${modelNameUpperCamel} get(@Param("id") Integer id,@Param("code") String code);

    List<${modelNameUpperCamel}> query(${dtoNameUpperCamel} ${dtoNameLowerCamel});

    int findByCode(@Param("code")String code);

    int updateBatch(@Param("ids") List<Integer> ids, @Param("status") String status);
}