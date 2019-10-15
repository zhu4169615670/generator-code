
package ${basePackage}.service.impl;

import ${basePackage}.dao.${modelNameUpperCamel}Dao;
import ${basePackage}.model.${modelNameUpperCamel};
import ${basePackage}.service.${modelNameUpperCamel}Service;
import ${basePackage}.dto.${dtoNameUpperCamel};
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import com.baison.e3plus.common.core.util.LoginUtils;
import com.baison.e3plus.common.message.Result;
import com.baison.e3plus.common.message.ResultData;
import com.baison.e3plus.common.message.ResultPageData;
import java.util.List;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.baison.e3plus.common.tool.PageTool;
import com.baison.e3plus.common.tool.UuidUtil;
import cn.hutool.json.JSONUtil;
import static com.github.pagehelper.page.PageMethod.startPage;
import com.baison.e3plus.biz.support.api.util.Constant;



/**
* @ Author     ：${author}
* @ Date       ：Created in ${date}
* @ Description：
* @ Modified By：
*/
@Service
public class ${modelNameUpperCamel}ServiceImpl implements ${modelNameUpperCamel}Service {

    private static PageTool pageTool = PageTool.getPageToolImpl();

    @Autowired
    private ${modelNameUpperCamel}Dao ${modelNameLowerCamel}Dao;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void  create(${modelNameUpperCamel} ${modelNameLowerCamel}) {
        ${modelNameLowerCamel}.setCreateTime(new Date());
        ${modelNameLowerCamel}.setModifyTime(new Date());
        ${modelNameLowerCamel}.setCreateBy(Constant.ADMIN);
        ${modelNameLowerCamel}Dao.create(${modelNameLowerCamel});
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result  update(${modelNameUpperCamel} ${modelNameLowerCamel}) {
        ${modelNameLowerCamel}.setModifyTime(new Date());
        ${modelNameLowerCamel}.setModifyBy(Constant.ADMIN);
        ${modelNameLowerCamel}Dao.update(${modelNameLowerCamel});
        return Result.success();
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result delete(Integer id) {
        ${modelNameLowerCamel}Dao.delete(id);
        return Result.success();
    }

    @Override
    public ${modelNameUpperCamel} get(Integer id) {
        return ${modelNameLowerCamel}Dao.get(id);
    }

    @Override
    public Result<ResultPageData<#noparse><List</#noparse><${modelNameUpperCamel}>>> query(${dtoNameUpperCamel} ${dtoNameLowerCamel}, int pageNum, int pageSize) {
        startPage(pageNum, pageSize);
        Page<${modelNameUpperCamel}> page = ${modelNameLowerCamel}Dao.query(${dtoNameLowerCamel});
        PageInfo<${modelNameUpperCamel}> pageInfo = new PageInfo<>(page);
        return pageTool.getPageInfo(pageInfo, pageSize, pageNum);
    }

}