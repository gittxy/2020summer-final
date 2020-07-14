package com.example.bx_web.service.impl;

import com.example.bx_web.mapper.UserMapper;
import com.example.bx_web.pojo.User;
import com.example.bx_web.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public User userLogin(String username,String password,int role_id){ return this.userMapper.userLogin(username,password,role_id);}

    @Override
    public void userRegister(String username, String password, int role_id) {
        this.userMapper.userRegister(username,password,role_id);
    }

    @Override
    public User userinfo(int user_id) {
        return this.userMapper.userinfo(user_id);
    }

    @Override
    public void modifyinfo(int user_id, String username, String password, String location) {
        this.userMapper.modifyinfo(user_id,username,password,location);
    }

    @Override
    public void rechargeMember(int user_id) {
        this.userMapper.rechargeMember(user_id);
    }

    @Override
    public void points(int user_id, int points) {
        this.userMapper.points(user_id,points);
    }

    @Override
    public void add(int user_id, String username, String nickname, String password, String gender, String email, String phone, String zip_code,
                    String location, int age, String country, String detail_address, String identity, int active, String code, Timestamp end,
                    Timestamp created, int points, int role_id) {
        this.userMapper.add(user_id,username,nickname,password,gender,email,phone,zip_code,location,age,country,detail_address,identity,
                active,code,end,created,points,role_id);
    }

    @Override
    public void delete(int user_id) {
        this.userMapper.delete(user_id);
    }

    @Override
    public List<User> getAllUser(){
        return this.userMapper.getAllUser();
    }
}
