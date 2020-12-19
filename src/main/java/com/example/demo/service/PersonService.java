package com.example.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.bean.Person;
import com.example.demo.mapper.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class PersonService {
    @Autowired(required = false)
    private PersonMapper personMapper;

    private Map<String, Object> getPersonInfo(Person person){
        Map<String, Object> map = new HashMap<>();
        map.put("username",person.getUsername());
        map.put("password",person.getPassword());
        map.put("age",person.getAge());
        map.put("gender",person.getGender());
        map.put("teleno",person.getTeleno());
        map.put("email",person.getEmail());
        map.put("time",person.getTime());
        return map;
    }

    private JSONObject makePersonJSON(String statusCode, Person person){
        JSONObject checkResult = new JSONObject();
        checkResult.put("statusCode",statusCode);
        if (person != null){
            Map<String, Object> map = getPersonInfo(person);
            checkResult.put("userInfo",map);
        }
        else{
            checkResult.put("userInfo",null);
        }
        return checkResult;
    }

    public JSONObject checkLogin(String username,String password){
        Person person = personMapper.checkLogin(username);

        // 查询结果，包含状态码和个人信息（如果正确的话）
        JSONObject checkResult ;
        if (person == null){
            // 未找到该人
            checkResult = makePersonJSON("404",null);
            return checkResult;
        }
        else {
            if (person.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))){
                // 正确
                checkResult = makePersonJSON("200",person);
                return checkResult;
            }
            else{
                // 密码错误
                checkResult = makePersonJSON("407",null);
                return checkResult;
            }
        }
    }

//    private JSONObject makeAddJSON(String statusCode,Person person){
//        JSONObject addResult = new JSONObject();
//        addResult.put("statusCode",statusCode);
//        if (person != null){
//            Map<String, Object> map = getPersonInfo(person);
//            addResult.put("userInfo",map);
//        }else{
//            addResult.put("userInfo",null);
//        }
//        return addResult;
//    }

    public JSONObject addPerson(String username,String password,String age,String gender,String teleno,String email){
        // 首先判断用户名是否存在，存在直接返回错误
        JSONObject addResult ;
        if (personMapper.findPersonByUsername(username) != null){
            // 已经存在
            return makePersonJSON("401",null);
        }

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(new Date());// new Date()为获取当前系统时间

        personMapper.addPerson(username, password, age, gender, teleno, email,time);
        addResult = makePersonJSON("200", personMapper.findPersonByUsername(username));
        return addResult;
    }

    public JSONObject queryPerson(String username){
        // 首先判断用户名是否存在，不存在直接返回错误
        Person person = personMapper.findPersonByUsername(username);
        if (person == null){
            return makePersonJSON("401",null);
        }
        else {
            return makePersonJSON("200",person);
        }
    }

    public JSONObject updatePerson(String username,String password,String age,String teleno,String email){
        // 首先判断用户名是否存在，不存在直接返回错误
        Person person = personMapper.findPersonByUsername(username);
        if (person == null){
            return makePersonJSON("401",null);
        }
        else{
            personMapper.updatePerson(username, password, age, teleno, email);
            return makePersonJSON("200",personMapper.findPersonByUsername(username));
        }
    }
}
