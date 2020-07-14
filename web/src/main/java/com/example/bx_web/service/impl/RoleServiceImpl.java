package com.example.bx_web.service.impl;


import com.example.bx_web.mapper.RoleMapper;

import com.example.bx_web.pojo.Role;
import com.example.bx_web.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

/**
 * 角色role的实现类
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Resource
	private RoleMapper roleMapper;

	@Override
	public void add(Long role_id,String name, String code,String description, Timestamp created, Timestamp updated) {
		roleMapper.add(role_id,name,code,description,created,updated);
	}

	@Override
	public void edit(Long role_id,String name, String code,String description, Timestamp created, Timestamp updated) {
		roleMapper.edit(role_id,name,code,description,created,updated);
	}

	@Override
	public void delete(Long role_id) {
		roleMapper.delete(role_id);
	}

	@Override
	public List<Role> getAllRole() {
		return roleMapper.getAllRole();
	}


}
