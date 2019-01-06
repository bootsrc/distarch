package com.appjishu.swordboot.util;

import com.appjishu.swordboot.boot.AppContextHolder;
import com.appjishu.swordboot.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestUtil {
    private static final Logger log = LoggerFactory.getLogger(TestUtil.class);

    public static void testAppContextHolder() {
        log.info("--testAppContextHolder start---");
        Student student0 = (Student) AppContextHolder.getBean("myStudent");
        Student student1 = (Student) AppContextHolder.getBean(Student.class);

        // 如果"student0 == student1"成立，则验证了AppContextHolder的确能获取到Spring容器ApplicationContext
        // 而且Spring容器默认注册bean使用的是单例模式
        log.info("student0==student1? {}", student0 == student1);
        log.info("-------------------");
        log.info("student0.name={}", student0.getName());
        log.info("student0.address={}", student0.getAddress());
        log.info("-------------------");
        log.info("student1.name={}", student1.getName());
        log.info("student1.address={}", student1.getAddress());
        log.info("--testAppContextHolder done---");
    }
}
