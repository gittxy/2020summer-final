package com.example.bx_web.service;

import com.example.bx_web.pojo.Role;

import java.sql.Timestamp;
import java.util.List;


public interface RoleService {
	void add(Long role_id, String name, String code, String description, Timestamp created, Timestamp updated);
	void edit(Long role_id, String name, String code, String description, Timestamp created, Timestamp updated);
	void delete(Long role_id);
	List<Role> getAllRole();
}
