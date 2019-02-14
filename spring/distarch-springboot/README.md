# sword-springboot

<code>com.appjishu.swordboot.boot.AppContextHolder</code>
<br/>

应用场景: <br/>
一般情况下，使用SpringMVC/SpringBoot的时候，各种bean注册到Spring容器里了，然后在需要这个bean的地方，<br/>
使用<code>@Autowired</code>或者<code>@Resource</code>标注的bean都可以被自动注入。 但是在某些场景下，<br/>
需要手动注入。比如在一个Util里面，这个Util里面的方法都是static的，这个时候，如果需要获取Spring容器中的某个<br/>
bean，或者获取到ApplicationContext, 这个时候，就需要一个ApplicationContext Holder的东西，这里命名为<code>AppContextHolder</code>

其实Spring里有一个接口就是为了这个应用场景而生的<code>ApplicationContextAware</code> <br/>
本人就开发了一个<code>com.appjishu.swordboot.boot.AppContextHolder</code>实现了这个接口, 源码如下: <br/>
```java
package com.appjishu.swordboot.boot;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 *
 * @author liushaoming
 *
 */
@Component
public class AppContextHolder implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (AppContextHolder.applicationContext == null) {
            AppContextHolder.applicationContext = applicationContext;
        }
    }

    // 获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    // 通过name获取 Bean.
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    // 通过class获取Bean.
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    // 通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }
}
```
使用的时候，很简单， 就像使用Spring原生的ApplicationContext一样， 现在要获取一个已经注册到容器里的一个bean <code>Student</code> <br/>
```java
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
```
使用AppContextHolder获取bean的操作
```java
Student student0 = (Student) AppContextHolder.getBean("myStudent");
// 或者
Student student1 = (Student) AppContextHolder.getBean(Student.class);
log.info("student0.name={}", student0.getName());
```
测试方法:
在idea/eclipse里，导入maven项目sword-springboot， 右键运行SwordBootApp -> run as -> Java Application <br/>
这是一个springboot项目，打开浏览器访问地址[http://localhost:15000/test](http://localhost:15000/test) <br/>
可以运行测试AppContextHolder的测试代码， 贴出TestUtil里的方法
```java
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
```
打印结果: 说明测试成功了
```
2019-01-06 15:43:47.563  INFO 21612 --- [io-15000-exec-1] com.appjishu.swordboot.util.TestUtil     : --testAppContextHolder start---
2019-01-06 15:43:47.564  INFO 21612 --- [io-15000-exec-1] com.appjishu.swordboot.util.TestUtil     : student0==student1? true
2019-01-06 15:43:47.564  INFO 21612 --- [io-15000-exec-1] com.appjishu.swordboot.util.TestUtil     : -------------------
2019-01-06 15:43:47.565  INFO 21612 --- [io-15000-exec-1] com.appjishu.swordboot.util.TestUtil     : student0.name=Frank Liu
2019-01-06 15:43:47.565  INFO 21612 --- [io-15000-exec-1] com.appjishu.swordboot.util.TestUtil     : student0.address=Shanghai
2019-01-06 15:43:47.566  INFO 21612 --- [io-15000-exec-1] com.appjishu.swordboot.util.TestUtil     : -------------------
2019-01-06 15:43:47.566  INFO 21612 --- [io-15000-exec-1] com.appjishu.swordboot.util.TestUtil     : student1.name=Frank Liu
2019-01-06 15:43:47.566  INFO 21612 --- [io-15000-exec-1] com.appjishu.swordboot.util.TestUtil     : student1.address=Shanghai
2019-01-06 15:43:47.566  INFO 21612 --- [io-15000-exec-1] com.appjishu.swordboot.util.TestUtil     : --testAppContextHolder done---
```

注意事项: <br/>
AppContextHolder在SpringBoot里使用，只需要在类名<code>AppContextHolder</code>前标注<code>@Component</code> <br/>
如果在SpringMVC里使用的，需要用@Component标注类AppContextHolder, 并且在applicationContext.xml里面设置<component-span> <br/>
包含AppContextHolder所在的package，  或者直接用<code>bean</code>标签来注册它。
