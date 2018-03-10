package com.adam;

import java.sql.*;

public class Main {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306";
        String user = "root";
        String passwd = "root";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            //1.加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            //2.建立连接
            connection = DriverManager.getConnection(url, user, passwd);
            //3.创建Statement
            statement = connection.createStatement();
            //4.执行查询
            String sql = "show databases";
            resultSet = statement.executeQuery(sql);
            //5.处理结果
            resultSet.absolute(4);
            System.out.println("Query: " + resultSet.getString("Database"));
            /*while (resultSet.next()) {
                resultSet.absolute(4);
                String database = resultSet.getString("Database");
                System.out.println("Query: " + database);
            }*/
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            //6.关闭连接
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (NullPointerException e) {
                System.out.println("数据库连接未建立或查询操作有误！");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
