package org.flylib.cassandrademo.app;

import org.flylib.cassandrademo.service.PersonService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author liushaoming
 * @create 2017-11-29 14:38
 **/
public class CassandraDemoApp {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext("application.xml");
        appContext.start();
        PersonService service =  appContext.getBean(PersonService.class);
        service.test();
    }
}
