package org.flylib.cassandrademo.repository;

import org.flylib.cassandrademo.pojo.Person;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface PersonRepository extends CrudRepository<Person, String> {
    @Query("select * from Person where id=?1 and name=?2")
    List<Person> findByIdAndName(String id, String name);

    /**
     * 会报错的, 不能这样用
     * @param name
     * @return
     */
//    @Query("select * from Person where name=?1 ALLOW FILTERING")
//    List<Person> findByName1(String name);

    @Query("select * from Person where name=:name ALLOW FILTERING")
    List<Person> findByName(@Param("name") String name);

    @Query("update person set record = record + [ :record ] where id=:id ")
    int addRecord(@Param("id") String id, @Param("record") String record);
}
