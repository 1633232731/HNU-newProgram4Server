package com.example.demo.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.demo.bean.Person;
import com.example.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PersonController {

    @Autowired
    private PersonService personService;

    /**
     * 检查登录信息
     *
     * @param request url参数
     * @return JSONObject
     * statusCode 200为登陆成功，404未未找到，407为密码错误
     * 登陆成功返回个人信息
     */

    @RequestMapping(value = "/login")
    @ResponseBody
    public JSONObject checkLogin(HttpServletRequest request){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println(username);
        return personService.checkLogin(username,password);
    }

    /**
     * 注册
     *
     * @param request url参数
     * @return JSONObject
     * statusCode 200为注册成功，401为用户名重复
     */

    @RequestMapping(value = "/addPerson")
    @ResponseBody
    public JSONObject addPerson(HttpServletRequest request){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String age = request.getParameter("age");
        String gender = request.getParameter("gender");
        String teleno = request.getParameter("teleno");
        String email = request.getParameter("email");
        return personService.addPerson(username,password,age,gender,teleno,email);
    }

    /**
     * 获取信息
     *
     * @param request url参数
     * @return JSONObject
     * statusCode 200为注册成功，401为用户不存在
     */

    @RequestMapping(value = "/queryPerson")
    @ResponseBody
    public JSONObject queryPerson(HttpServletRequest request){
        String username = request.getParameter("username");
        return personService.queryPerson(username);
    }

    /**
     * 修改信息，一般在修改信息前需要先获取信息
     * username 和 gender 不能改
     *
     * @param request url参数
     * @return JSONObject
     * statusCode 200为修改成功，401为用户名重复
     */

    @RequestMapping(value = "/updatePerson")
    @ResponseBody
    public JSONObject updatePerson(HttpServletRequest request){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String age = request.getParameter("age");
        String teleno = request.getParameter("teleno");
        String email = request.getParameter("email");
        return personService.updatePerson(username,password,age,teleno,email);
    }
}
