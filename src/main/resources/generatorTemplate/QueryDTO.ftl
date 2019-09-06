
package ${basePackage}.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

import java.io.Serializable;


/**
* @ Author     ：${author}
* @ Date       ：Created in ${date}
* @ Description： 查询Dto  根据需要删减无效参数
* @ Modified By：
*/
@Data
public class ${modelNameUpperCamel}QueryDTO  implements Serializable{

<#list baseDataList as data>
    @ApiModelProperty(value = "${data.columnComment}")
    private ${data.columnType} ${data.columnName};

</#list>
}