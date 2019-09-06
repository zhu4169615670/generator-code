
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
import com.baison.e3plus.common.message.Result;
import com.baison.e3plus.common.message.ResultData;
import com.baison.e3plus.common.message.ResultPageData;
import java.util.List;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.baison.e3plus.common.tool.PageTool;
import com.baison.e3plus.common.tool.UuidUtil;
import cn.hutool.json.JSONUtil;



/**
* @ Author     ：${author}
* @ Date       ：Created in ${date}
* @ Description：
* @ Modified By：
*/
@Service
public class ${modelNameUpperCamel}ServiceImpl implements ${modelNameUpperCamel}Service {

    private static PageTool pageTool = PageTool.getPageToolImpl();

    private static UuidUtil uuid = UuidUtil.getUuidUtilImpl();

    @Autowired
    private ${modelNameUpperCamel}Dao ${modelNameLowerCamel}Dao;


    @Transactional
    @Override
    public Result<?>  create(${modelNameUpperCamel} ${modelNameLowerCamel}) {
        ${modelNameLowerCamel}.setCreateTime(new Date());
        ${modelNameLowerCamel}.setModifyTime(new Date());
        ${modelNameLowerCamel}Dao.create(${modelNameLowerCamel});
        return Result.success("添加成功！");
    }

    @Transactional
    @Override
    public Result<?>  update(${modelNameUpperCamel} ${modelNameLowerCamel}) {
        ${modelNameLowerCamel}.setModifyTime(new Date());
        ${modelNameLowerCamel}Dao.update(${modelNameLowerCamel});
        return Result.success("修改成功！");
    }


    @Transactional
    @Override
    public Result<?>  delete(Integer id) {
        ${modelNameLowerCamel}Dao.delete(id);
        return Result.success("删除成功！");
    }

    @Override
    public Result<ResultData<#noparse><List</#noparse><${modelNameUpperCamel}>>> findById(Integer id) {
        return pageTool.findById(${modelNameLowerCamel}Dao.findById(id));
    }

    @Override
    public Result<ResultPageData<#noparse><List</#noparse><${modelNameUpperCamel}>>> queryPage(${dtoNameUpperCamel} ${dtoNameLowerCamel}, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<${modelNameUpperCamel}> page = ${modelNameLowerCamel}Dao.queryPage(${dtoNameLowerCamel});
        PageInfo<${modelNameUpperCamel}> pageInfo = new PageInfo<>(page);
        return pageTool.getPageInfo(pageInfo, pageSize, pageNum);
    }

}