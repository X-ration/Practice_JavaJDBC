package com.adam;

import com.adam.util.JdbcUtil;

import java.sql.*;
import java.util.Arrays;

/**
 * @author adam
 * 创建于 2018-03-10 14:43.
 */
public class UtilMain {
    public static void main(String[] args) throws SQLException {
        JdbcUtil.initAll();
        /*
        //Basic Queries
        ResultSet resultSet = JdbcUtil.executeQuery("select * from user_tables");
        while(resultSet.next()) {
            String db = resultSet.getString("TABLE_NAME");
            System.out.println("Query: " + db);
        }
        PreparedStatement preparedStatement = JdbcUtil.createPreparedStatement("select ename from emp where ename like ?");
        preparedStatement.setString(1,"%A%");
        resultSet = JdbcUtil.executeQueryPrepared();
        while(resultSet.next()) {
            String db = resultSet.getString("ename");
            System.out.println("Query: " + db);
        }*/
        /*
        //Batch Operations: insert values
        int empno = 6669;
        PreparedStatement preparedStatement = JdbcUtil.createPreparedStatement("insert into emp values(?,'Adam','Student',6666,'01-MAY-97',9999,100,10)");
        preparedStatement.setInt(1,++empno);
        JdbcUtil.batchOperationsAddPrepared();
        preparedStatement.setInt(1,++empno);
        JdbcUtil.batchOperationsAddPrepared();
        int[] res = JdbcUtil.batchOperationsExecutePrepared();
        System.out.println("结果:" + Arrays.toString(res));
        JdbcUtil.batchOperationsClearPrepared();*/
        //acquire meta data
        DatabaseMetaData databaseMetaData = JdbcUtil.getMetaDatabase();
        System.out.println("DatabaseMetaData测试，用户名：" + databaseMetaData.getUserName());
        PreparedStatement preparedStatement = JdbcUtil.createPreparedStatement("insert into emp values(?,'Adam','Student',6666,'01-MAY-97',9999,100,10)");
        ParameterMetaData parameterMetaData = JdbcUtil.getMetaParameter();
        System.out.println("ParameterMetaData测试，参数个数：" + parameterMetaData.getParameterCount());
        JdbcUtil.createStatement();
        ResultSet resultSet = JdbcUtil.executeQuery("select * from user_tables");
        ResultSetMetaData resultSetMetaData = JdbcUtil.getMetaResultSet();
        System.out.println("ResultSetMetaData测试，结果集列数：" + resultSetMetaData.getColumnCount());
        JdbcUtil.closeAll();
    }
}
