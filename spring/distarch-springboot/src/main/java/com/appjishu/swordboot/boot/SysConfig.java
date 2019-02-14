package com.appjishu.swordboot.boot;

import com.appjishu.swordboot.model.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SysConfig {
    @Bean(name = "myStudent")
    public Student student() {
        Student student=new Student();
        student.setName("Frank Liu");
        student.setAddress("Shanghai");
        return student;
    }
}
