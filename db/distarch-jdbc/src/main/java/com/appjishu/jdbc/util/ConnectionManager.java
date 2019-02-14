package com.appjishu.jdbc.util;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {
    private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<Connection>();

    /**
     * 采用ThreadLocal封装Connection
     *
     * @return
     */
    public static Connection getConnection() {
        //获得线程变量connectionHolder的值conn
        Connection conn = connectionHolder.get();
        if (conn == null) {
            //如果连接为空，则创建连接，另一个工具类，创建连接
            conn = DBUtil.getConnection();
            //将局部变量connectionHolder的值设置为conn
            connectionHolder.set(conn);
        }
        return conn;
    }


    /**
     * 关闭连接和从线程变量中删除conn
     */
    public static void closeConnection() {
        //获得线程变量connectionHolder的值conn
        Connection conn = connectionHolder.get();
        if (conn != null) {
            try {
                //关闭连接
                conn.close();
                //从线程局部变量中移除conn，如果没有移除掉，下次还会用这个已经关闭的连接，就会出错
                connectionHolder.remove();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *开启事务，手动开启
     */
    public static void beginTransaction(Connection conn){
        try {
            //如果连接存在，再设置连接，否则会出错
            if (conn != null){
                //默认conn是自动提交，
                if (conn.getAutoCommit()){
                    //关闭自动提交，即是手动开启事务
                    conn.setAutoCommit(false);
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * 提交事务
     */
    public static void commitTransaction(Connection conn){
        try{
            if (conn != null){
                if (!conn.getAutoCommit()){
                    conn.commit();
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * 回滚事务
     */
    public static void rollbackTransaction(Connection conn){
        try {
            if (conn != null){
                if(!conn.getAutoCommit()){
                    conn.rollback();
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
