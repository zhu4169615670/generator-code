package com.example.demo.model;

import com.example.demo.generator.config.CodeGenerator;
import lombok.Data;
import org.apache.catalina.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @ Author     ：pengfei.zhu
 * @ Date       ：Created in 10:55 2019/9/6
 * @ Description：需要得一些配置参数
 * @ Modified By：
 */
@Data
public class CodeGeneratorParamModel {

    private String jdbcUrl;

    private String jdbcUserName;

    private String jdbcPwd;

    /**
      *包名 例如com.example.demo
     */
    private String basePackagePath;

    /**
     * /com/example/ 例如  电脑得磁盘路径
     */
    private String dtoAbsolutePath;

    private String modelAbsolutePath;

    private String controllerAbsolutePath;

    private String serviceAbsolutePath;

    private String serviceImplAbsolutePath;

    private String daoAbsolutePath;

    public boolean getIsGeneratorDTO() {
        return isGeneratorDTO;
    }

    public void setGeneratorDTO(boolean generatorDTO) {
        isGeneratorDTO = generatorDTO;
    }

    /**
     *作者
     */
    private String author;

    /**
     *表名
     */
    private String[] tableNames;

    private boolean isGeneratorDTO = true;


    public static void main(String[] args) {
        List<CodeGenerator> shareGroupLockLogs = new ArrayList<>();
        shareGroupLockLogs.add(new CodeGenerator());
        System.out.println(shareGroupLockLogs.get(0).getClass().getSimpleName());
    }

}
