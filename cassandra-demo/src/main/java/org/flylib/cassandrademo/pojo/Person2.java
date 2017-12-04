package org.flylib.cassandrademo.pojo;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * @author liushaoming
 * @create 2017-11-29 13:47
 **/
@Table(value = "person2")
public class Person2 {
    @PrimaryKey
    private Person2Key pKey;

    private int age;

    public Person2Key getpKey() {
        return pKey;
    }

    public void setpKey(Person2Key pKey) {
        this.pKey = pKey;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person2 [pKey=" + pKey + ", age=" + age + "]";
    }
}