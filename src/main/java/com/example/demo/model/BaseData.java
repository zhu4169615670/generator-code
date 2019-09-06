package com.example.demo.model;

/**
 * @ Author     ：pengfei.zhu
 * @ Date       ：Created in 13:35 2019/9/5
 * @ Description：
 * @ Modified By：
 */
public class BaseData {

    private String columnComment;

    private String columnType;

    private String columnName;

    public String getJdbcColumnName() {
        return jdbcColumnName;
    }

    public void setJdbcColumnName(String jdbcColumnName) {
        this.jdbcColumnName = jdbcColumnName;
    }

    private String jdbcColumnName;

    //数据类型
    private String jdbcType;

    //是否主键
    private int isPrimaryKey=0;

    public int getIsPrimaryKey() {
        return isPrimaryKey;
    }

    public void setIsPrimaryKey(int isPrimaryKey) {
        this.isPrimaryKey = isPrimaryKey;
    }

    public String getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
}
