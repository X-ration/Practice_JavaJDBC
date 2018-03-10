package com.adam;

import com.adam.util.JdbcUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author adam
 * 创建于 2018-03-10 14:43.
 */
public class UtilMain {
    public static void main(String[] args) throws SQLException {
        JdbcUtil.initAll();
        ResultSet resultSet = JdbcUtil.executeQuery("select * from user_tables");
        while(resultSet.next()) {
            String db = resultSet.getString("TABLE_NAME");
            System.out.println("Query: " + db);
        }
        JdbcUtil.closeAll();
    }
}
