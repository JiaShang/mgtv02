package com.shang.mgtv02.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @ResponseBody
    @GetMapping("/users")
    public Object[] map(){
       List<Map<String,Object>> list = jdbcTemplate.queryForList("select * from users");
       return list.toArray();
    }

    @ResponseBody
    @GetMapping("/user/{name}/{password}")
    public Integer verify(){
        String sql="select count(*) from users where name = ?,password = ?";
        Integer count = jdbcTemplate.execute(sql);
//        Object object = jdbcTemplate.queryForObject("select * from users where name ={name} and password = {password}");
        return count;
    }
}
