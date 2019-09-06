
package ${basePackage}.service.impl;

import ${basePackage}.dao.${modelNameUpperCamel}Dao;
import ${basePackage}.model.${modelNameUpperCamel};
import ${basePackage}.service.${modelNameUpperCamel}Service;
import ${basePackage}.dto.${dtoNameUpperCamel};
import com.baison.e3plus.common.message.Result;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;


/**
* @ Author     ：${author}
* @ Date       ：Created in ${date}
* @ Description：
* @ Modified By：
*/
@Service
public class ${modelNameUpperCamel}ServiceImpl implements ${modelNameUpperCamel}Service {

    @Autowired
    private ${modelNameUpperCamel}Dao ${modelNameLowerCamel}Dao;


    @Transactional
    @Override
    public Result create(${modelNameUpperCamel} ${modelNameLowerCamel}) {
        ${modelNameLowerCamel} = this.set${modelNameUpperCamel}(${modelNameLowerCamel});
        ${modelNameLowerCamel}Dao.create(${modelNameLowerCamel});
        return new Result().success("添加成功！");
    }

    @Transactional
    @Override
    public Result update(${modelNameUpperCamel} ${modelNameLowerCamel}) {
        ${modelNameLowerCamel}.setModifyTime(new Date());
        ${modelNameLowerCamel}Dao.update(${modelNameLowerCamel});
        return new Result().success("修改成功！");
    }


    @Transactional
    @Override
    public Result delete(Integer id) {
        ${modelNameLowerCamel}Dao.delete(id);
        return new Result().success("删除成功！");
    }

    @Override
    public Result findById(Integer id) {
        return new Result().success(${modelNameLowerCamel}Dao.findById(id));
    }

    @Override
    public Result queryPage(${dtoNameUpperCamel} ${dtoNameLowerCamel}, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<${modelNameUpperCamel}> page = ${modelNameLowerCamel}Dao.queryPage(${dtoNameLowerCamel});
            PageInfo<${modelNameUpperCamel}> pageInfo = new PageInfo<>(page);
            return new Result().success(pageInfo.getList(),(int)pageInfo.getTotal(),pageSize,pageNum,pageInfo.getPages());
        }

    public ${modelNameUpperCamel} set${modelNameUpperCamel}(${modelNameUpperCamel} ${modelNameLowerCamel}) {
        ${modelNameLowerCamel}.setCreateTime(new Date());
        ${modelNameLowerCamel}.setModifyTime(new Date());
        return ${modelNameLowerCamel};
    }


}