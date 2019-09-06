
package ${basePackage}.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import io.swagger.annotations.ApiModel;

import java.util.Date;

import java.io.Serializable;

/**
* @ Author     ：${author}
* @ Date       ：Created in ${date}
* @ Description：
* @ Modified By：
*/
@ApiModel(value = "${tableName}表的字段描述")
@Data
public class ${modelNameUpperCamel} implements Serializable {

    <#list baseDataList as data>
    @ApiModelProperty(value = "${data.columnComment}")
    private ${data.columnType} ${data.columnName};
    </#list>
}