package org.flylib.cassandrademo.pojo;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.List;

/**
 * Entity
 *
 * @author liushaoming
 * @create 2017-11-29 13:22
 **/
@Table(value="person")
public class Person {
    // 主键
    @PrimaryKey
    @Column(value = "id")
    private String id;

    // 列名 与数据库列名一致时可不加
    @Column(value = "name")
    private String name;

    @Column(value = "age")
    private int age;

    @Column(value = "record")
    private List<String> record;

    // 支持构造函数
    public Person(String id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getRecord() {
        return record;
    }

    public void setRecord(List<String> record) {
        this.record = record;
    }

    @Override
    public String toString() {
        return "Person [id=" + id + ", name=" + name + ", age=" + age + ", records=" +  record.toString()
                +"]";
    }
}
