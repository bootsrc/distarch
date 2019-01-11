package com.appjishu.jdbc.demo.service;

import com.appjishu.jdbc.demo.dao.ProductDAO;
import com.appjishu.jdbc.demo.dao.UserDAO;
import com.appjishu.jdbc.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;

@Service
public class DemoService {
    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private UserDAO userDAO;


    public void handle() {
        Connection connection = ConnectionManager.getConnection();

        try {
            ConnectionManager.beginTransaction(connection);
            productDAO.add();
            boolean test = true;
            if (test) {
                userDAO.add();
            }
            ConnectionManager.commitTransaction(connection);
        } catch (Exception e) {
            e.printStackTrace();
            ConnectionManager.rollbackTransaction(connection);
        }
        System.out.println("---Done---");
    }
}
