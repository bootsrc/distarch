package com.appjishu.jdbc.demo.dao;

import com.appjishu.jdbc.util.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

@Repository
public class UserDAO {

    public int add() throws SQLException {
        /**
         * user_table表不存在，会抛异常
         */
        Random random = new Random();
        String sql ="insert into user_table (name, price) values  ('iphone xr', " + random.nextInt(100) + " );";
        Connection conn =  ConnectionManager.getConnection();
        Statement statement = conn.createStatement();
        int updatedCount =statement.executeUpdate(sql);
        return updatedCount;
    }
}
