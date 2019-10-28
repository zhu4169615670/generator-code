
package ${basePackage}.controller;

import ${basePackage}.service.${modelNameUpperCamel}Service;
import ${basePackage}.model.${modelNameUpperCamel};
import ${basePackage}.dto.${modelNameUpperCamel}QueryDTO;
import org.springframework.beans.factory.annotation.Autowired;;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import com.baison.e3plus.common.message.Result;
import com.baison.e3plus.common.message.ResultData;
import com.baison.e3plus.common.message.ResultPageData;
import com.baison.e3plus.common.cncore.common.Status;
import com.github.pagehelper.PageInfo;
import java.util.List;

/**
* @author ${author}
* @Date ${date}
* @Description
* @Modified By
*/
@Api(tags = "${modelNameUpperCamel}管理")
@RestController
@RequestMapping(value = "${baseRequestMapping}")
public class ${modelNameUpperCamel}Controller {

    @Autowired
    private ${modelNameUpperCamel}Service ${modelNameLowerCamel}Service;

    @ApiImplicitParam(value = "保存接口", dataTypeClass = ${modelNameUpperCamel}.class)
    @ApiOperation(value = "保存接口入参", notes = "保存接口入参", httpMethod = "POST")
    @PostMapping(value = "/create", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    public Result<Integer>  create(@RequestBody ${modelNameUpperCamel} ${modelNameLowerCamel}) {
       ${modelNameLowerCamel}Service.create(${modelNameLowerCamel});
       return Result.success(${modelNameLowerCamel}.getId());
    }

    @ApiImplicitParam(value = "修改接口", dataTypeClass = ${modelNameUpperCamel}.class)
    @ApiOperation(value = "修改接口入参", notes = "修改接口入参", httpMethod = "POST")
    @PostMapping(value = "/update")
    public Result update(@RequestBody ${modelNameUpperCamel} ${modelNameLowerCamel}) {
        return ${modelNameLowerCamel}Service.update(${modelNameLowerCamel});
    }

    @ApiImplicitParam(name = "id", value = "主键ID", dataType = "String")
    @ApiOperation(value = "删除接口入参", notes = "删除接口入参", httpMethod = "DELETE")
    @DeleteMapping(value = "/delete/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        return ${modelNameLowerCamel}Service.delete(id);
    }

    @ApiImplicitParam(name = "id", value = "主键ID", dataType = "int")
    @ApiOperation(value = "根据主键ID查询接口入参", notes = "根据主键ID查询接口入参", httpMethod = "GET")
    @GetMapping(value = "/get")
    public Result<${modelNameUpperCamel}> get(@RequestParam(value = "id",required = false) Integer id,@RequestParam(value = "code",required = false) String code) {
        return Result.success(${modelNameLowerCamel}Service.get(id,code));
    }

    @ApiImplicitParams({ @ApiImplicitParam(name = "pageNum", value = "当前页码", required = true, dataType = "int"),
    @ApiImplicitParam(name = "pageSize", value = "每页显示的条数", required = true, dataType = "int"),
    @ApiImplicitParam(value = "分页查询接口", dataTypeClass = ${modelNameUpperCamel}.class) })
    @ApiOperation(value = "分页查询接口入参", notes = "分页查询接口入参", httpMethod = "GET")
    @GetMapping(value = "/query")
    public Result<PageInfo<#noparse><</#noparse>${modelNameUpperCamel}>> query(${dtoNameUpperCamel} ${dtoNameLowerCamel}, @RequestParam("pageNum") int pageNum,
    @RequestParam("pageSize") int pageSize) {
         return Result.success(${modelNameLowerCamel}Service.query(${dtoNameLowerCamel},pageNum, pageSize));
    }

    @ApiImplicitParam(value = "启用接口", dataType = "int", allowMultiple = true)
    @ApiOperation(value = "启用接口", notes = "启用接口", httpMethod = "POST")
    @PostMapping(value = "/enable")
    public Result enable(@RequestParam("ids") List<Integer> ids) {
        ${modelNameLowerCamel}Service.updateBatch(ids, Status.ENABLE);
        return Result.success();
    }

    @ApiImplicitParam(value = "停用接口", dataType = "int", allowMultiple = true)
    @ApiOperation(value = "启用接口", notes = "启用接口", httpMethod = "POST")
    @PostMapping(value = "/disable")
    public Result disable(@RequestParam("ids") List<Integer> ids) {
        ${modelNameLowerCamel}Service.updateBatch(ids, Status.DISABLE);
        return Result.success();
    }
}
