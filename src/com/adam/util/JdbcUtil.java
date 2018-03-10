package com.adam.util;

import java.sql.*;
import java.util.ResourceBundle;

/**
 * @author adam
 * 创建于 2018-03-10 14:17.
 */
public class JdbcUtil {

    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("jdbc");
    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    private static Connection connection = null;
    private static Statement statement = null;
    private static ResultSet resultSet = null;

    static {
        DRIVER = resourceBundle.getString("driver");
        URL = resourceBundle.getString("url");
        USERNAME = resourceBundle.getString("user");
        PASSWORD = resourceBundle.getString("passwd");
    }

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void init() {
        System.out.println("数据库驱动已加载");//真正的初始化在静态语句块中完成
    }

    public static void initAll() {
        getConnection();
        createStatement();
    }

    public static void closeAll() {
        closeResultSet();
        closeStatement();
        closeConnection();
    }

    public static void getConnection() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("获取数据库连接失败");
            e.printStackTrace();
        }
    }

    public static void closeConnection() {
        if(connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("关闭数据库连接时出现异常");
                e.printStackTrace();
            }
        }
    }

    public static void createStatement() {
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("创建Statement出现异常");
        }
    }

    public static void closeStatement() {
        if(statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                System.out.println("关闭Statement出现异常");
                e.printStackTrace();
            }
        }
    }

    public static ResultSet executeQuery(String sql) {
        resultSet = null;
        try {
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("进行数据库查询时出现异常");
            e.printStackTrace();
        }
        return resultSet;
    }

    public static void closeResultSet() {
        if(resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                System.out.println("关闭ResultSet时出现异常");
                e.printStackTrace();
            }
        }
    }

}
