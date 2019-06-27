package com.hc.utils;

/**
 * Created by HC on 2017/12/7.
 */
public class Tools {
    //首字母转小写
    public static String First2LowerCase(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    //首字母转大写
    public static String First2UpperCase(String s) {
        if (Character.isUpperCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    //将表字段名中的下划线_去掉
    public static String field2Property(String columnName) {
        while (columnName.indexOf("_") > -1) {
            int index = columnName.indexOf("_");
            columnName = columnName.substring(0, index) + columnName.substring(index, index + 2).toUpperCase().substring(1)
                    + columnName.substring(index + 2, columnName.length());
        }
        return columnName;
    }

    /**
     * 获取表名所对应的实体类的名称，默认每个表名都有一个前缀，将前缀给干掉
     * @param table
     * @return
     */
    public static  String getEntryName(String table) {
        String tb = table.trim();
        String temp = Tools.First2UpperCase(tb.substring(tb.indexOf('_') + 1));
        return field2Property(temp);        
    }
    
}
