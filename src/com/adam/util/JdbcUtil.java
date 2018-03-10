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
    private static PreparedStatement preparedStatement = null;
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
        closeStatements();
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
            e.printStackTrace();
        }
    }

    /**
     * 创建PreparedStatement对象。
     * @param sql 含有？的SQL执行语句
     * @return 成功创建时返回PreparedStatement对象引用，失败时返回null
     */
    public static PreparedStatement createPreparedStatement(String sql) {
        preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            System.out.println("创建PreparedStatement出现异常");
            e.printStackTrace();
        }
        return preparedStatement;
    }

    public static void closeStatements() {
        if(statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                System.out.println("关闭Statement出现异常");
                e.printStackTrace();
            }
        }
        if(preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                System.out.println("关闭PreparedStatement出现异常");
                e.printStackTrace();
            }
        }
    }

    public static ResultSet executeQuery(String sql) {
        resultSet = null;
        try {
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("使用Statement进行数据库查询时出现异常");
            e.printStackTrace();
        }
        return resultSet;
    }

    /**
     * 查询前要先对PreparedStatement设置相应的参数，因为参数可能是各种类型，暂时没有为此封装一个方法。
     * @return 查询后的ResultSet结果集。
     */
    public static ResultSet executeQueryPrepared() {
        resultSet = null;
        try {
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            System.out.println("使用PreparedStatement进行数据库查询时出现异常");
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
