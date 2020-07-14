package com.example.bx_web.service;

import com.example.bx_web.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

public interface UserService {
    User userLogin(String username,String password,int role_id);
    void userRegister(String username,String password,int role_id);
    User userinfo(int user_id);
    void modifyinfo(int user_id, String username, String password, String location);
    void rechargeMember(int user_id);
    void points(int user_id,int points);
    void add(int user_id, String username, String nickname, String password, String gender, String email, String phone, String zip_code,
             String location, int age, String country, String detail_address, String identity, int active, String code, Timestamp end,
             Timestamp created, int points, int role_id);
    void delete(int user_id);
    List<User> getAllUser();
}
