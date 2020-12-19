package com.example.demo.mapper;


import com.example.demo.bean.Person;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface PersonMapper {
    @Select("SELECT * FROM personandroid")
    List<Person> findAllPerson();

    @Select("SELECT * FROM personandroid where username='${username}'")
    Person findPersonByUsername(String username);

    @Select("select * from personandroid where username='${username}'")
    Person checkLogin(String username);

    @Insert("insert into personandroid(username,password,age,gender,teleno,email,time)values('${username}', MD5('${password}'),'${age}','${gender}','${teleno}','${email}','${time}')")
    void addPerson(String username,String password,String age,String gender,String teleno,String email,String time);

    @Update("update personandroid set password=MD5('${password}'),age='${age}',teleno='${teleno}',email='${email}' where username = '${username}' ")
    void updatePerson(String username,String password,String age,String teleno,String email);

}
