package com.example.demo.utils;

import com.example.demo.generator.config.CodeGenerator;
import com.example.demo.model.BaseData;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @ Author     ：pengfei.zhu
 * @ Date       ：Created in 13:34 2019/9/5
 * @ Description：
 * @ Modified By：
 */
public class DatabaseUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(DatabaseUtil.class);

    private static final String DRIVER = CodeGenerator.JDBC_DIVER_CLASS_NAME.trim();
    private static final String URL = CodeGenerator.JDBC_URL.trim();
    private static final String USERNAME = CodeGenerator.JDBC_USERNAME.trim();
    private static final String PASSWORD = CodeGenerator.JDBC_PASSWORD.trim();
    private static final String SQL = "SELECT * FROM ";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            LOGGER.error("can not load jdbc driver", e);
        }
    }

    /**
     * 获取数据库连接
     *
     * @return
     */
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            LOGGER.error("get connection failure", e);
        }
        return conn;
    }

    /**
     * 关闭数据库连接
     * @param conn
     */
    public static void closeConnection(Connection conn) {
        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("close connection failure", e);
            }
        }
    }

    /**
     * 获取表中字段名称
     * @param tableName 表名
     * @return
     */
    public static List<String> getColumnNames(String tableName) {
        List<String> columnNames = new ArrayList<>();
        //与数据库的连接
        Connection conn = getConnection();
        PreparedStatement pStemt = null;
        String tableSql = SQL + tableName;
        try {
            pStemt = conn.prepareStatement(tableSql);
            //结果集元数据
            ResultSetMetaData rsmd = pStemt.getMetaData();
            //表列数
            int size = rsmd.getColumnCount();
            for (int i = 0; i < size; i++) {
                columnNames.add(rsmd.getColumnName(i + 1));
            }
        } catch (SQLException e) {
            LOGGER.error("getColumnNames failure", e);
        } finally {
            if (pStemt != null) {
                try {
                    pStemt.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    LOGGER.error("getColumnNames close pstem and connection failure", e);
                }
            }
        }
        return columnNames;
    }

    /**
     * 获取表中所有字段类型
     * @param tableName
     * @return
     */
    public static List<String> getColumnTypes(String tableName) {
        List<String> columnTypes = new ArrayList<>();
        //与数据库的连接
        Connection conn = getConnection();
        PreparedStatement pStemt = null;
        String tableSql = SQL + tableName;
        try {
            pStemt = conn.prepareStatement(tableSql);
            //结果集元数据
            ResultSetMetaData rsmd = pStemt.getMetaData();
            //表列数
            int size = rsmd.getColumnCount();
            DatabaseMetaData dbmd= conn.getMetaData();
            ResultSet rs = dbmd.getPrimaryKeys(null,null,tableName);
            String keyName = null;
            while (rs.next()) {
                keyName=rs.getString(4);
            }
            for (int i = 0; i < size; i++) {
                columnTypes.add(rsmd.getColumnTypeName(i + 1));
            }
        } catch (SQLException e) {
            LOGGER.error("getColumnTypes failure", e);
        } finally {
            if (pStemt != null) {
                try {
                    pStemt.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    LOGGER.error("getColumnTypes close pstem and connection failure", e);
                }
            }
        }
        return columnTypes;
    }

    /**
     * 获取表中字段的注释
     * @param tableName
     * @return
     */
    public static List<String> getColumnComments(String tableName) {
        Connection conn = getConnection();
        PreparedStatement pStemt = null;
        String tableSql = SQL + tableName;
        List<String> columnComments = new ArrayList<>();
        ResultSet rs = null;
        try {
            pStemt = conn.prepareStatement(tableSql);
            rs = pStemt.executeQuery("show full columns from " + tableName);
            while (rs.next()) {
                columnComments.add(rs.getString("Comment"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    LOGGER.error("getColumnComments close ResultSet and connection failure", e);
                }
            }
        }
        return columnComments;
    }

    //存所有的数据库列名
    public static String ALL_COLUNMS = "";

    public static List<BaseData> getBaseDataList(String tableName){
        List<String> columnNames = getColumnNames(tableName);
        if(StringUtils.isEmpty(ALL_COLUNMS)){
            for (String columnName : columnNames) {
                ALL_COLUNMS += columnName+",";
            }
            ALL_COLUNMS = ALL_COLUNMS.substring(0,ALL_COLUNMS.length() - 1);
        }
        List<String> columnTypes = getColumnTypes(tableName);
        List<String> columnComments = getColumnComments(tableName);
        String primariyKey = getPrimariyKey(tableName);
        if(StringUtils.isEmpty(primariyKey)){
            throw new RuntimeException("查询数据库主键失败，请设置数据库主键！");
        }
        List<BaseData> list = new ArrayList<BaseData>();
        for (int i = 0 ; i<columnNames.size(); i++){
            BaseData baseData = new BaseData();
            baseData.setColumnName(firstUpperCamelCase(columnNames.get(i)));
            baseData.setJdbcColumnName(columnNames.get(i));
            String columnType = null;
            if(columnNames.get(i).equals(primariyKey)){
                baseData.setIsPrimaryKey(1);
            }
            switch (columnTypes.get(i)){
                case "VARCHAR":
                    baseData.setJdbcType("VARCHAR");
                    columnType = "String";
                    break;
                case "DATETIME":
                    baseData.setJdbcType("TIMESTAMP");
                    columnType = "Date";
                    break;
                case "TIMESTAMP":
                    baseData.setJdbcType("TIMESTAMP");
                    columnType = "Date";
                    break;
                case "BIGINT":
                    baseData.setJdbcType("BIGINT");
                    columnType = "Long";
                    break;
                case "INT":
                    baseData.setJdbcType("INTEGER");
                    columnType = "Integer";
                    break;
                case "TINYINT":
                    baseData.setJdbcType("INTEGER");
                    columnType = "Integer";
                    break;
                case "DECIMAL":
                    baseData.setJdbcType("DECIMAL");
                    columnType = "BigDecimal";
                    break;
                case "TEXT":
                    baseData.setJdbcType("TEXT");
                    columnType = "String";
                    break;
                default:
                    columnType = "未知类型";
                    System.out.print("存在不支持类型！请手写。");
                    throw new RuntimeException("存在不支持类型！请手写。");

            }
            baseData.setColumnType(columnType);
            baseData.setColumnComment(columnComments.get(i));
            list.add(baseData);
        }
        return list;
    }

    /**
     * @author: pengfei.zhu
     * @description:获取数据库的主键
     * @return: void
     * @author: pengfei.zhu
     * @time: 2019/9/5 16:49
     */
    private static String getPrimariyKey(String tableName) {
        //与数据库的连接
        Connection conn = getConnection();
        DatabaseMetaData dbmd= null;
        ResultSet rs = null;
        String keyName = null;
        try {
            dbmd = conn.getMetaData();
             rs = dbmd.getPrimaryKeys(null,null,tableName);
            while (rs.next()){
                keyName=rs.getString(4);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    LOGGER.error("getColumnComments close ResultSet and connection failure", e);
                }
            }
        }
        return keyName;
    }

    public static String firstUpperCamelCase(String str) {
        if (StringUtils.isNotBlank(str)) {
            str = str.replace("T_", "");
            str = str.toLowerCase();
            String[] strs = str.split("_");
            if (strs.length == 1) {
                return str.substring(0, 1).toLowerCase()+ str.substring(1);
            } else {
                String convertedStr = "";
                for (int i = 0; i < strs.length; i++) {
                    convertedStr += firstLetterUpper(strs[i]);
                }
                return convertedStr.substring(0, 1).toLowerCase()+ convertedStr.substring(1);
            }
        }
        return str;
    }

    public static String firstLetterUpper(String str) {
        if (StringUtils.isNotBlank(str)) {
            str = str.replace("T_", "");
            str = str.toLowerCase();
            return str.substring(0, 1).toUpperCase()
                    + str.substring(1, str.length());
        }
        return str;
    }

}
