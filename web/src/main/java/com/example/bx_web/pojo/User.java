package com.example.bx_web.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.sql.Timestamp;

@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
public class User implements Serializable {

    private int user_id;
    private String username;
    private String nickname;
    private String password;
    private String gender;
    private String email;
    private String phone;
    private String zip_code;
    private String location;
    private int age;
    private String country;
    private String detail_address;
    private String identity;
    private int active;
    private String code;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp end;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp created;
    private int points;
    private int role_id;

    public User() {

    }

    public User(int user_id) {
        this.user_id = user_id;
    }

    public User(String username, String password, int role_id) {
        this.username = username;
        this.password = password;
        this.role_id = role_id;
    }

    public User(int user_id, String username, String nickname, String password, String gender, String email, String phone, String zip_code, String location, int age, String country, String detail_address, String identity, int active, String code, Timestamp end, Timestamp created, int points, int role_id) {
        this.user_id = user_id;
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.zip_code = zip_code;
        this.location = location;
        this.age = age;
        this.country = country;
        this.detail_address = detail_address;
        this.identity = identity;
        this.active = active;
        this.code = code;
        this.end = end;
        this.created = created;
        this.points = points;
        this.role_id = role_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDetail_address() {
        return detail_address;
    }

    public void setDetail_address(String detail_address) {
        this.detail_address = detail_address;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }
}
