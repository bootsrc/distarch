package com.appjishu.jdbc.demo.service;

import com.appjishu.jdbc.demo.dao.ProductDAO;
import com.appjishu.jdbc.demo.dao.UserDAO;
import com.appjishu.jdbc.util.ConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;

@Service
public class DemoService {
    private static final Logger log = LoggerFactory.getLogger(DemoService.class);

    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private UserDAO userDAO;


    public void handle() {
        Connection connection = ConnectionManager.getConnection();

        try {
            int count0 = productDAO.count();
            log.info("count0={}", count0);
            ConnectionManager.beginTransaction(connection);
            productDAO.add();
            int count1 = productDAO.count();
            log.info("count1={}", count1);
            boolean test = true;
            if (test) {
                userDAO.add();
            }
            ConnectionManager.commitTransaction(connection);
        } catch (Exception e) {
            e.printStackTrace();
            ConnectionManager.rollbackTransaction(connection);
        }
        int count2 = 0;
        boolean nowAutoCommit = false;
        try {
            count2 = productDAO.count();
            nowAutoCommit = connection.getAutoCommit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        log.info("getAutoCommit()={}", nowAutoCommit);
        log.info("count2={}", count2);

        log.info("---Done---");
    }
}
