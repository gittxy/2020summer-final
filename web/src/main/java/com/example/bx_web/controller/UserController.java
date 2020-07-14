package com.example.bx_web.controller;

import com.example.bx_web.pojo.Role;
import com.example.bx_web.pojo.User;
import com.example.bx_web.service.UserService;
import com.example.bx_web.utils.JsonUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;


@RestController
public class UserController {
    @Autowired
    public UserService userService;
    /**
     * 登录控制器，前后端分离用的不同协议和端口，所以需要加入@CrossOrigin支持跨域。
     * 给VueLoginInfoVo对象加入@Valid注解，并在参数中加入BindingResult来获取错误信息。
     * 在逻辑处理中我们判断BindingResult知否含有错误信息，如果有错误信息，则直接返回错误信息。
     */

    @RequestMapping("/login")
    public String Login(User user) throws JSONException {
        User iuser=userService.userLogin(user.getUsername(),user.getPassword(),user.getRole_id());
        if(iuser!=null){
            System.out.println(JsonUtils.putJson(iuser));
            return JsonUtils.putJson(iuser);
        }
        else{
            System.out.println("failed");
            return null;
        }
    }
    @RequestMapping(value = "/register")
    public String Register(User user) throws JSONException {
//        System.out.println(user.getUsername());
        userService.userRegister(user.getUsername(),user.getPassword(),user.getRole_id());
        return JsonUtils.putJson("success");
    }
    @RequestMapping(value = "/userinfo")
    public String Userinfo(User user) throws JSONException {
        User user2= userService.userinfo(user.getUser_id());
        System.out.println(JsonUtils.putJson(user2));
        return JsonUtils.putJson(user2);
    }
    @RequestMapping(value = "/modifyinfo")
    public String Modifyinfo(User user) throws JSONException {
        userService.modifyinfo(user.getUser_id(),user.getUsername(),user.getPassword(),user.getLocation());
        return JsonUtils.putJson("success");
    }
    @RequestMapping(value = "/recharge")
    public String Recharge(User user) throws JSONException {
//        String test="{\"user_id\":\"2\"}";
        userService.rechargeMember(user.getUser_id());
        return JsonUtils.putJson("success");
    }

    @RequestMapping(value = "/addUser")
    public String AddUser(@RequestBody String jsonStr) throws JSONException {
        Gson gson = new Gson();
        User user = gson.fromJson(jsonStr, User.class);
        userService.add(user.getUser_id(),user.getUsername(),user.getNickname(),user.getPassword(),user.getGender(),user.getEmail(),
                user.getPhone(),user.getZip_code(),user.getLocation(),user.getAge(),user.getCountry(),user.getDetail_address(),
                user.getIdentity(),user.getActive(),user.getCode(),user.getEnd(),user.getCreated(),user.getPoints(),user.getRole_id());
        return JsonUtils.putJson("success");
    }

    @RequestMapping(value = "/deleteUser")
    public String DeleteUser(@RequestBody String jsonStr) throws JSONException {
        Gson gson=new Gson();
        Type type = new TypeToken<List<User>>(){}.getType();
        List<User> list = gson.fromJson(jsonStr, type);
        for (int i = 0; i < list.size(); i++) {
            userService.delete(list.get(i).getUser_id());
        }
        return JsonUtils.putJson("success");
    }

    @RequestMapping(value = "/User")
    public List<User> GetTotalUser() throws JSONException {
        List<User> list = userService.getAllUser();

        return list;
    }

}

