package org.flylib.cassandrademo.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.Assignment;
import com.datastax.driver.core.querybuilder.Update;
import org.flylib.cassandrademo.pojo.Person;
import org.flylib.cassandrademo.repository.PersonRepository;
import org.flylib.cassandrademo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.config.CassandraCqlSessionFactoryBean;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.cql.CqlOperations;
import org.springframework.data.cassandra.core.cql.PreparedStatementBinder;
import org.springframework.stereotype.Service;

import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.datastax.driver.core.querybuilder.Update;

import javax.management.Query;


/**
 * @author liushaoming
 * @create 2017-11-29 14:03
 **/
@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CassandraOperations cassandraOperations;

    @Autowired
    private CassandraTemplate cassandraTemplate;

    @Autowired
    private CassandraCqlSessionFactoryBean sessionFactoryBean;

    @Override
        public void test() {
        //通过Repository查询
        Iterable<Person> iterable = personRepository.findAll();
        Iterator<Person> it = iterable.iterator();
        System.out.println("==>findAll:");
        while (it.hasNext()) {
            Person p = it.next();
            System.out.println(p.toString());
        }

        //通过Repository 自定义查询查询
//        List<Person> list = personRepository.findByName("one", "11111");
        List<Person> list = personRepository.findByName("one");

        System.out.println("==>findByName:");
        for (Person person : list) {System.out.println(person.toString());
            System.out.println("=====> " + person.toString());
        }



        System.out.println("Try to add value to one 'list column'---------------------");

//        for (int i = 0; i < 3; i++) {
//            personRepository.addRecord("t" +, "my record" + i);
//        }
//        List<Person> list2 = personRepository.findByName("one");
//        System.out.println("==>findByName again:");
//        for (Person p : list2) {
//            System.out.println("=====> " + p.toString());
//        }

//        Update update = QueryBuilder.update("person");
//        update.setConsistencyLevel(ConsistencyLevel.ONE);
////        update.with(QueryBuilder.set("age", 35));
//        Assignment assignment = QueryBuilder.add("record", "xxx");
//        update.with(assignment);
//        update.where(QueryBuilder.eq("id", "1"));
////        cassandraOperations.execute(update);
//        cassandraOperations.update(update, Person.class);

        /**
         * 如何在list类型的列里增加一个数据
         */
        //  method 1
//        String updateColumnCql = "update person set record = record + ['yyy'] where id='1';";
//        boolean cqlSuccess = cassandraTemplate.getCqlOperations().execute(updateColumnCql);

        // method 2 PreparedStatement
        String updateColumnCql = "update person set record = record +  ?   where id= ? ;";
        Session session = sessionFactoryBean.getObject();
        PreparedStatement preparedStatement = session.prepare(updateColumnCql);
        List<String> addedColumn = new ArrayList<String>();
        addedColumn.add("Added");
        BoundStatement statement = preparedStatement.bind(addedColumn, "1");
        session.execute(statement);


        //通过cassandraOperations查询
        Select select = QueryBuilder.select().from("person");
        select.where(QueryBuilder.eq("id", "1"));
        Person person = cassandraOperations.selectOne(select, Person.class);
        System.out.println("==> Finally, cassandraOperations:");
        System.out.println(person.toString());
    }

}