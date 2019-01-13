package com.appjishu.jdbc.demo.dao;

import com.appjishu.jdbc.util.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

@Repository
public class ProductDAO {
    public int add() throws SQLException {
        Random random = new Random();
        String sql = "insert into product (name, price) values  ('iphone xr', " + random.nextInt(100) + " );";
        Connection conn = ConnectionManager.getConnection();
        Statement statement = conn.createStatement();
        int updatedCount = statement.executeUpdate(sql);
        return updatedCount;
    }

    public int count() throws SQLException {
        String sql = "SELECT COUNT(1) FROM product ";
        Connection connection = ConnectionManager.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        return resultSet.next() ? resultSet.getInt(1) : -1;
    }
}
