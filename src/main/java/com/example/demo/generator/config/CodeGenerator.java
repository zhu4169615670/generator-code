package com.example.demo.generator.config;

import com.example.demo.model.BaseData;
import com.example.demo.model.CodeGeneratorParamModel;
import com.example.demo.utils.DatabaseUtil;
import com.google.common.base.CaseFormat;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @ Author     ：pengfei.zhu
 * @ Date       ：Created in 13:36 2019/9/5
 * @ Description：
 * @ Modified By：
 */
public class CodeGenerator {

    /**
     * JDBC配置，请修改为你项目的实际配置
     */
//    private static  String JDBC_URL = "jdbc:mysql://baisondb001.mysql.database.chinacloudapi.cn:3306/guns?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai";
    public static  String JDBC_URL = null;
//    private static  String JDBC_USERNAME = "baison@baisondb001";
    public static  String JDBC_USERNAME = null;
//    private static  String JDBC_PASSWORD = "fHzap123!@#";
    public static  String JDBC_PASSWORD = null;
    public static  String JDBC_DIVER_CLASS_NAME = "com.mysql.jdbc.Driver";

    /**
     * 存放数据库查询到的列
     */
    private  static  List<BaseData> baseDataList = new ArrayList<>();
    /**
     * 模块名称
     */
    static final String MODULE_NAME = "demo";
    /**
     * 模块路径
     */
//    static final String MODULE_DIR = "/com-example-" + CodeGenerator.MODULE_NAME;
    static final String MODULE_DIR = "";
    /**
     * 模块基础路径
     */
//    public static  String BASE_PACKAGE = "com.example." + CodeGenerator.MODULE_NAME;

    public static  String BASE_PACKAGE = null;


    /**
     * 项目在硬盘上的基础路径
     */
    private static final String PROJECT_PATH = System.getProperty("user.dir");
    /**
     * 模板位置
     */
    private static final String TEMPLATE_FILE_PATH = PROJECT_PATH + "/src/main/resources/generatorTemplate";
    /**
     * java文件路径
     */
    private static final String JAVA_PATH = MODULE_DIR + "/src/main/java/";
    /**
     * 资源文件路径
     */
    private static final String RESOURCES_PATH = MODULE_DIR + "/src/main/resources";
    /**
     * 生成的Service存放路径
     */
//    private static  String PACKAGE_PATH_SERVICE = "/com/example/" + CodeGenerator.MODULE_NAME + "/service/";
    private static  String PACKAGE_PATH_SERVICE =null;
    /**
     * 生成的Service实现存放路径
     */
//    private static  String PACKAGE_PATH_SERVICE_IMPL = "/com/example/" + CodeGenerator.MODULE_NAME + "/service/impl/";
    private static  String PACKAGE_PATH_SERVICE_IMPL = null;
    /**
     * 生成的Controller存放路径
     */
//    private static  String PACKAGE_PATH_CONTROLLER = "/com/example/" + CodeGenerator.MODULE_NAME + "/controller/";
    private static  String PACKAGE_PATH_CONTROLLER = null;
    /**
     * 生成的Mapper存放路径
     */
//    private static  String PACKAGE_DAO_PATH= "/com/example/" + CodeGenerator.MODULE_NAME + "/dao/";
    private static  String PACKAGE_DAO_PATH= null;
    /**
     * 生成的Mapper的xml存放路径
     */
    private static  String PACKAGE_MAPPER_XMLPATH= PROJECT_PATH+RESOURCES_PATH+"/mapper/";


    /**
     *--------------------------------------------------------------------------------------
     */

    //按需加载


    /**
     * 生成的RequestVO存放路径
     */
//    private static   String PACKAGE_PATH_REQUESTVO = "/com/example/" + CodeGenerator.MODULE_NAME + "/dto/";
    private static   String PACKAGE_PATH_REQUESTVO = null;

    /**
     * 生成的Model存放路径
     */
//    private static    String PACKAGE_PATH_MODEL = "/com/example/" + CodeGenerator.MODULE_NAME + "/model/";
    private static    String PACKAGE_PATH_MODEL = null;
    /**
     * 生成的ResponseVO存放路径
     */
//    private static final String PACKAGE_PATH_RESPONSEVO = "/com/example/" + CodeGenerator.MODULE_NAME + "/vo/Response/";
    /**
     * 生成的EntityVO存放路径
     */
//    private static final String PACKAGE_PATH_ENTITYVO = "/com/example/" + CodeGenerator.MODULE_NAME + "/vo/Entity/";

    private static   final String AUTHOR = "pengfei.zhu";

    private static final String DATE = new SimpleDateFormat("HH:mm yyyy/MM/dd").format(new Date());

    private  CodeGeneratorParamModel codeGeneratorParamModel;

  /*  public static void main(String[] args) {
        genCode("sys_dept");
        //genCode("输入表名","输入自定义Model名称");
    }*/


    public static void main(String[] args) throws Exception {
        CodeGeneratorParamModel codeGeneratorParamModel = new CodeGeneratorParamModel();
        codeGeneratorParamModel.setJdbcUrl("jdbc:mysql://baisondb001.mysql.database.chinacloudapi.cn:3306/e3plus-support-dev?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai");
        codeGeneratorParamModel.setAuthor("pengfei.zhu");
        codeGeneratorParamModel.setJdbcPwd("fHzap123!@#");
        codeGeneratorParamModel.setJdbcUserName("baison@baisondb001");
        codeGeneratorParamModel.setBasePackagePath("com.example.demo");
        codeGeneratorParamModel.setDtoAbsolutePath("/com/example/demo/dto/");
        codeGeneratorParamModel.setModelAbsolutePath("/com/example/demo/model/");
        codeGeneratorParamModel.setControllerAbsolutePath("/com/example/demo/controller/");
        codeGeneratorParamModel.setServiceAbsolutePath("/com/example/demo/service/");
        codeGeneratorParamModel.setServiceImplAbsolutePath("/com/example/demo/service/impl/");
        codeGeneratorParamModel.setDaoAbsolutePath("/com/example/demo/dao/");
        codeGeneratorParamModel.setTableNames(new String[]{"currency_type_operate_log"});
        CodeGenerator codeGenerator = new CodeGenerator();
        codeGenerator.genCode(codeGeneratorParamModel);

    }
    /**
     * 通过数据表名称生成代码，Model 名称通过解析数据表名称获得，下划线转大驼峰的形式。
     * 如输入表名称 "t_user_detail" 将生成 TUserDetail、TUserDetailMapper、TUserDetailService ...
     * @param
     */
    public void genCode(CodeGeneratorParamModel codeGeneratorParamModel) throws Exception{
        this.codeGeneratorParamModel = codeGeneratorParamModel;
        String[] tableNames = codeGeneratorParamModel.getTableNames();
        if(StringUtils.isEmpty(codeGeneratorParamModel.getJdbcUrl())){
            throw new RuntimeException("数据库url不能为空！");
        }
        if(StringUtils.isEmpty(codeGeneratorParamModel.getJdbcUserName())){
            throw new RuntimeException("数据库username不能为空！");
        }
        if(StringUtils.isEmpty(codeGeneratorParamModel.getJdbcPwd())){
            throw new RuntimeException("数据库pwd不能为空！");
        }
        if(StringUtils.isEmpty(codeGeneratorParamModel.getBasePackagePath())){
            throw new RuntimeException("请正确设置基础包名,例如：com.baidu.***");
        }
        if(StringUtils.isEmpty(codeGeneratorParamModel.getModelAbsolutePath())){
            throw new RuntimeException("请正确设置磁盘对应Model路径,例如 com/example/,注意：不是包名");
        }
        if(StringUtils.isEmpty(codeGeneratorParamModel.getDtoAbsolutePath())){
            throw new RuntimeException("请正确设置磁盘对应Dto路径,例如 com/example/,注意：不是包名");
        }
        if(StringUtils.isEmpty(codeGeneratorParamModel.getServiceAbsolutePath())){
            throw new RuntimeException("请正确设置磁盘对应Service路径,例如 com/example/,注意：不是包名");
        }
        if(StringUtils.isEmpty(codeGeneratorParamModel.getServiceImplAbsolutePath())){
            throw new RuntimeException("请正确设置磁盘对应ServiceImpl路径,例如 com/example/,注意：不是包名");
        }
        if(StringUtils.isEmpty(codeGeneratorParamModel.getDaoAbsolutePath())){
            throw new RuntimeException("请正确设置磁盘对应Dao路径,例如 com/example/,注意：不是包名");
        }
        if(StringUtils.isEmpty(codeGeneratorParamModel.getAuthor())){
            codeGeneratorParamModel.setAuthor(AUTHOR);
        }
        if(ArrayUtils.isEmpty(tableNames)){
            throw new RuntimeException("数据库表名不能为空！");
        }
        BASE_PACKAGE = codeGeneratorParamModel.getBasePackagePath();
        JDBC_URL = codeGeneratorParamModel.getJdbcUrl();
        JDBC_USERNAME = codeGeneratorParamModel.getJdbcUserName();
        JDBC_PASSWORD = codeGeneratorParamModel.getJdbcPwd();
        PACKAGE_PATH_REQUESTVO = codeGeneratorParamModel.getDtoAbsolutePath();
        PACKAGE_PATH_MODEL = codeGeneratorParamModel.getModelAbsolutePath();
        PACKAGE_PATH_CONTROLLER = codeGeneratorParamModel.getControllerAbsolutePath();
        PACKAGE_PATH_SERVICE = codeGeneratorParamModel.getServiceAbsolutePath();
        PACKAGE_PATH_SERVICE_IMPL = codeGeneratorParamModel.getServiceImplAbsolutePath();
        PACKAGE_DAO_PATH = codeGeneratorParamModel.getDaoAbsolutePath();
        for (String tableName : tableNames) {
            genCode(tableName, null);
        }
    }

    /**
     * 通过数据表名称，和自定义的 Model 名称生成代码
     * 如输入表名称 "t_user_detail" 和自定义的 Model 名称 "User" 将生成 User、UserMapper、UserService ...
     * @param tableName 数据表名称
     * @param modelName 自定义的 Model 名称
     */
    public  void genCode(String tableName, String modelName) {
        File file1 = new File(PROJECT_PATH + JAVA_PATH);
        File file2 = new File(PROJECT_PATH + RESOURCES_PATH);
        if (!file1.getAbsoluteFile().exists()) {
            file1.getAbsoluteFile().mkdirs();
        }
        if (!file2.getAbsoluteFile().exists()) {
            file2.getAbsoluteFile().mkdirs();
        }
        genModel(tableName,modelName);
        genRequestVO(tableName,modelName);
        genController(tableName, modelName);
        genService(tableName, modelName);
        genMapper(tableName, modelName);
//        genModelAndMapper(tableName, modelName);
//        genEntityVO(tableName,modelName);
//        genResponseVO(tableName,modelName);
    }



/*

    public static void genModelAndMapper(String tableName, String modelName) {
        Context context = new Context(ModelType.FLAT);
        context.setId("Potato");
        context.setTargetRuntime("MyBatis3Simple");
        context.addProperty(PropertyRegistry.CONTEXT_BEGINNING_DELIMITER, "`");
        context.addProperty(PropertyRegistry.CONTEXT_ENDING_DELIMITER, "`");

        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setConnectionURL(JDBC_URL);
        jdbcConnectionConfiguration.setUserId(JDBC_USERNAME);
        jdbcConnectionConfiguration.setPassword(JDBC_PASSWORD);
        jdbcConnectionConfiguration.setDriverClass(JDBC_DIVER_CLASS_NAME);
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);

        PluginConfiguration pluginConfiguration = new PluginConfiguration();
        pluginConfiguration.setConfigurationType("tk.mybatis.mapper.generator.MapperPlugin");
        pluginConfiguration.addProperty("mappers", CodeGenerator.MAPPER_INTERFACE_REFERENCE);
        context.addPluginConfiguration(pluginConfiguration);

        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        javaModelGeneratorConfiguration.setTargetProject(PROJECT_PATH + JAVA_PATH);
        javaModelGeneratorConfiguration.setTargetPackage(CodeGenerator.MODEL_PACKAGE);
        context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);

        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
        sqlMapGeneratorConfiguration.setTargetProject(PROJECT_PATH + RESOURCES_PATH);
        sqlMapGeneratorConfiguration.setTargetPackage("mapper");
        context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);

        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
        javaClientGeneratorConfiguration.setTargetProject(PROJECT_PATH + JAVA_PATH);
        javaClientGeneratorConfiguration.setTargetPackage(CodeGenerator.MAPPER_PACKAGE);
        javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
        context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);

        TableConfiguration tableConfiguration = new TableConfiguration(context);
        tableConfiguration.setTableName(tableName);
        tableConfiguration.setDomainObjectName(modelName);
        tableConfiguration.setGeneratedKey(new GeneratedKey("id", "Mysql", true, null));
        context.addTableConfiguration(tableConfiguration);

        List<String> warnings;
        MyBatisGenerator generator;
        try {
            Configuration config = new Configuration();
            config.addContext(context);
            config.validate();

            boolean overwrite = true;
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            warnings = new ArrayList<String>();
            generator = new MyBatisGenerator(config, callback, warnings);
            generator.generate(null);
        } catch (Exception e) {
            throw new RuntimeException("生成Model和Mapper失败", e);
        }

        if (generator.getGeneratedJavaFiles().isEmpty() || generator.getGeneratedXmlFiles().isEmpty()) {
            throw new RuntimeException("生成Model和Mapper失败：" + warnings);
        }
        if (StringUtils.isEmpty(modelName)){
            modelName = tableNameConvertUpperCamel(tableName);
        }
        System.out.println(modelName + ".java 生成成功");
        System.out.println(modelName + "Mapper.java 生成成功");
        System.out.println(modelName + "Mapper.xml 生成成功");
    }
*/

    public static void genMapper(String tableName, String modelName) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();
            Map<String, Object> data = new HashMap<>();
            data.put("date", DATE);
            data.put("author", AUTHOR);
            data.put("baseDataList", baseDataList);
            String modelNameUpperCamel = StringUtils.isEmpty(modelName) ? tableNameConvertUpperCamel(tableName) : modelName;
            data.put("modelNameUpperCamel", modelNameUpperCamel);
            data.put("modelNameLowerCamel", tableNameConvertLowerCamel(tableName));
            data.put("basePackage", CodeGenerator.BASE_PACKAGE);
            data.put("dtoNameUpperCamel", modelNameUpperCamel+"QueryDTO");
            data.put("dtoNameLowerCamel", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, modelNameUpperCamel+"QueryDTO"));
            File file = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_DAO_PATH + modelNameUpperCamel + "Dao.java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("dao.ftl").process(data,
                    new FileWriter(file));
            System.out.println(modelNameUpperCamel + "Dao.java 生成成功");

            File file1 = new File(PACKAGE_MAPPER_XMLPATH + modelNameUpperCamel + "Mapper.xml");
            if (!file1.getParentFile().exists()) {
                file1.getParentFile().mkdirs();
            }
            data.put("colunms", DatabaseUtil.ALL_COLUNMS);
            data.put("tablename", tableName);
            cfg.getTemplate("mapper.ftl").process(data,
                    new FileWriter(file1));
            System.out.println(modelNameUpperCamel + "Mapper.xml 生成成功");
        } catch (Exception e) {
            throw new RuntimeException("生成Service失败", e);
        }
    }

    public static void genService(String tableName, String modelName) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();
            Map<String, Object> data = new HashMap<>();
            data.put("date", DATE);
            data.put("author", AUTHOR);
            String modelNameUpperCamel = StringUtils.isEmpty(modelName) ? tableNameConvertUpperCamel(tableName) : modelName;
            data.put("modelNameUpperCamel", modelNameUpperCamel);
            data.put("modelNameLowerCamel", tableNameConvertLowerCamel(tableName));
            data.put("basePackage", CodeGenerator.BASE_PACKAGE);
            data.put("dtoNameUpperCamel", modelNameUpperCamel+"QueryDTO");
            data.put("dtoNameLowerCamel", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, modelNameUpperCamel+"QueryDTO"));
            File file = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_SERVICE + modelNameUpperCamel + "Service.java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("service.ftl").process(data,
                    new FileWriter(file));
            System.out.println(modelNameUpperCamel + "Service.java 生成成功");

            File file1 = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_SERVICE_IMPL + modelNameUpperCamel + "ServiceImpl.java");
            if (!file1.getParentFile().exists()) {
                file1.getParentFile().mkdirs();
            }
            cfg.getTemplate("service-impl.ftl").process(data,
                    new FileWriter(file1));
            System.out.println(modelNameUpperCamel + "ServiceImpl.java 生成成功");
        } catch (Exception e) {
            throw new RuntimeException("生成Service失败", e);
        }
    }

    public static void genController(String tableName, String modelName) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();
            String[] split = tableName.split("_");
            String tableLastName = split[1];
            String modelLastName = firstToCapital(tableLastName);
            Map<String, Object> data = new HashMap<>();
            data.put("date", DATE);
            data.put("author", AUTHOR);
            String modelNameUpperCamel = StringUtils.isEmpty(modelName) ? tableNameConvertUpperCamel(tableName) : modelName;
            data.put("baseRequestMapping", modelNameConvertMappingPath(modelNameUpperCamel));
            data.put("modelNameUpperCamel", modelNameUpperCamel);
            data.put("modelNameLowerCamel", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, modelNameUpperCamel));
            data.put("basePackage", CodeGenerator.BASE_PACKAGE);
            data.put("tableLastName", tableLastName);
            data.put("modelLastName", modelLastName);
            data.put("dtoNameUpperCamel", modelNameUpperCamel+"QueryDTO");
            data.put("dtoNameLowerCamel", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, modelNameUpperCamel+"QueryDTO"));
            data.put("modelName", MODULE_NAME);

            File file = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_CONTROLLER + modelNameUpperCamel + "Controller.java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("controller.ftl").process(data, new FileWriter(file));

            System.out.println(modelNameUpperCamel + "Controller.java 生成成功");
        } catch (Exception e) {
            throw new RuntimeException("生成Controller失败", e);
        }

    }


    public static void genModel(String tableName, String modelName) {
        genModel(tableName,modelName,PACKAGE_PATH_MODEL);

    }

    public static void genRequestVO(String tableName, String modelName) {
        genVO(tableName,modelName,PACKAGE_PATH_REQUESTVO,"QueryDTO");

    }

    /*public static void genResponseVO(String tableName, String modelName) {
        genVO(tableName,modelName,PACKAGE_PATH_RESPONSEVO,"ResponseVO");
    }*/

/*    public static void genEntityVO(String tableName, String modelName) {
        genVO(tableName,modelName,PACKAGE_PATH_ENTITYVO,"EntityVO");
    }*/


    public static void genModel(String tableName, String modelName,String packagePath) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();
            baseDataList = DatabaseUtil.getBaseDataList(tableName);
            Map<String, Object> data = new HashMap<>();
            data.put("date", DATE);
            data.put("author", AUTHOR);
            String modelNameUpperCamel = StringUtils.isEmpty(modelName) ? tableNameConvertUpperCamel(tableName) : modelName;
            data.put("baseRequestMapping", modelNameConvertMappingPath(modelNameUpperCamel));
            data.put("modelNameUpperCamel", modelNameUpperCamel);
            data.put("modelNameLowerCamel", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, modelNameUpperCamel));
            data.put("basePackage", CodeGenerator.BASE_PACKAGE);
            data.put("baseDataList", baseDataList);
            data.put("tableName", tableName);
            File file = new File(PROJECT_PATH + JAVA_PATH + packagePath + modelNameUpperCamel +".java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate( "model.ftl").process(data, new FileWriter(file));
            System.out.println(modelNameUpperCamel  + ".java 生成成功");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    public static void genVO(String tableName, String modelName,String packagePath,String className) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();
            baseDataList = DatabaseUtil.getBaseDataList(tableName);
            Map<String, Object> data = new HashMap<>();
            data.put("date", DATE);
            data.put("author", AUTHOR);
            String modelNameUpperCamel = StringUtils.isEmpty(modelName) ? tableNameConvertUpperCamel(tableName) : modelName;
            data.put("baseRequestMapping", modelNameConvertMappingPath(modelNameUpperCamel));
            data.put("modelNameUpperCamel", modelNameUpperCamel);
            data.put("modelNameLowerCamel", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, modelNameUpperCamel));
            data.put("basePackage", CodeGenerator.BASE_PACKAGE);
            data.put("dtoNameUpperCamel", modelNameUpperCamel+"QueryDTO");
            data.put("dtoNameLowerCamel", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, modelNameUpperCamel+"QueryDTO"));
            data.put("baseDataList", baseDataList);

            File file = new File(PROJECT_PATH + JAVA_PATH + packagePath + modelNameUpperCamel + className +".java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate(className +".ftl").process(data, new FileWriter(file));

            System.out.println(modelNameUpperCamel + className + ".java 生成成功");
        } catch (Exception e) {
            throw new RuntimeException(className, e);
        }

    }

    private static freemarker.template.Configuration getConfiguration() throws IOException {
        freemarker.template.Configuration cfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_23);
        cfg.setDirectoryForTemplateLoading(new File(TEMPLATE_FILE_PATH));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        return cfg;
    }

    private static String tableNameConvertLowerCamel(String tableName) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName.toLowerCase());
    }

    private static String tableNameConvertUpperCamel(String tableName) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName.toLowerCase());

    }

    private static String tableNameConvertMappingPath(String tableName) {
        //兼容使用大写的表名
        tableName = tableName.toLowerCase();
        return "/" + (tableName.contains("_") ? tableName.replaceAll("_", "/") : tableName);
    }

    private static String modelNameConvertMappingPath(String modelName) {
        String tableName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, modelName);
        return tableNameConvertMappingPath(tableName);
    }

    private static String packageConvertPath(String packageName) {
        return String.format("/%s/", packageName.contains(".") ? packageName.replaceAll("\\.", "/") : packageName);

    }
    //首字母大写
    private static String firstToCapital(String str){
        char[] chars = str.toCharArray();
        if (chars[0] >= 'a' && chars[0] <= 'z') {
            chars[0] = (char)(chars[0] - 32);
        }
        return new String(chars);
    }

}
