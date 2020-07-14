package com.example.bx_web.mapper;

import com.example.bx_web.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface UserMapper {
    User userLogin(@Param("username") String username,@Param("password") String password,@Param("role_id") int role_id);
    void userRegister(@Param("username") String username,@Param("password") String password,@Param("role_id") int role_id);
    User userinfo(int user_id);
    void modifyinfo(@Param("user_id") int user_id,@Param("username") String username, @Param("password") String password,
                    @Param("location") String location);
    void rechargeMember(int user_id);
    void points(@Param("user_id") int user_id,@Param("points") int points);
    int add(@Param("user_id") int user_id, @Param("username") String username, @Param("nickname") String nickname,
            @Param("password") String password, @Param("gender") String gender, @Param("email") String email,
            @Param("phone") String phone, @Param("zip_code") String zip_code, @Param("location") String location,
            @Param("age") int age, @Param("country") String country, @Param("detail_address") String detail_address,
            @Param("identity") String identity, @Param("active") int active, @Param("code") String code,
            @Param("end") Timestamp end, @Param("created") Timestamp created, @Param("points") int points, @Param("role_id") int role_id);
    int delete(@Param("user_id") int user_id);
    List<User> getAllUser();
}
