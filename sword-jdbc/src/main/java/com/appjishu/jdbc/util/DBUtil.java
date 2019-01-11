package com.appjishu.jdbc.util;

import com.appjishu.jdbc.config.DataSourceConfigConst;
import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DataSourceConfigConst.DRIVER_CLASS_NAME);

            MysqlDataSource mysqlDataSource = new MysqlConnectionPoolDataSource();
            mysqlDataSource.setUrl(DataSourceConfigConst.URL);
            mysqlDataSource.setUser(DataSourceConfigConst.USERNAME);
            mysqlDataSource.setPassword(DataSourceConfigConst.PASSWORD);
            connection = mysqlDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            connection = null;
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
            connection = null;
        }
        return connection;

    }
}
