package com.adam;

import com.adam.util.JdbcUtil;

import java.sql.*;
import java.util.Arrays;
import java.util.Random;

import static java.util.stream.Collectors.toList;

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
        /*DatabaseMetaData databaseMetaData = JdbcUtil.getMetaDatabase();
        System.out.println("DatabaseMetaData测试，用户名：" + databaseMetaData.getUserName());
        PreparedStatement preparedStatement = JdbcUtil.createPreparedStatement("insert into emp values(?,'Adam','Student',6666,'01-MAY-97',9999,100,10)");
        ParameterMetaData parameterMetaData = JdbcUtil.getMetaParameter();
        System.out.println("ParameterMetaData测试，参数个数：" + parameterMetaData.getParameterCount());
        JdbcUtil.createStatement();
        ResultSet resultSet = JdbcUtil.executeQuery("select * from user_tables");
        ResultSetMetaData resultSetMetaData = JdbcUtil.getMetaResultSet();
        System.out.println("ResultSetMetaData测试，结果集列数：" + resultSetMetaData.getColumnCount());*/
        //灌数据

        int threshold = 1000000, iniValue=100000;   //只需更改初始值和阈值即可，代表插入记录的id从初始值开始到阈值结束。
        int id = iniValue;
        String[] names = {"Adam","John","Charlie","Jack","Bob"};
        String[] infos = {"Adam0","John0","Charlie0","Jack0","Bob0"};
        PreparedStatement preparedStatement = JdbcUtil.createPreparedStatement("insert into test values(?,?,?)");
        Random random = new Random();
        long start = System.currentTimeMillis();
        while(id<threshold) {
            int name = random.nextInt(5);
            int info = random.nextInt(5);
            preparedStatement.setInt(1, id++);
            preparedStatement.setString(2, names[name]);
            preparedStatement.setString(3, infos[info]);
            //System.out.println("创建记录id"+id+((JdbcUtil.executeUpdatePrepared()==1)?"成功":"失败"));
            JdbcUtil.batchOperationsAddPrepared();
        }
        int[] res = JdbcUtil.batchOperationsExecutePrepared();
        long end = System.currentTimeMillis();
        System.out.println(Arrays.stream(res).mapToObj(i->(i>=0 || i==-2)?"成功":"失败").collect(toList()));
        System.out.println("本次更新"+(threshold-iniValue)+"条记录，共耗时"+(end-start)+"ms.");
        JdbcUtil.closeAll();
    }
}
