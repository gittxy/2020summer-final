package com.example.bx_web.mapper;

import com.example.bx_web.pojo.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface RoleMapper {
	void add(@Param("role_id") Long role_id, @Param("name") String name, @Param("code") String code, @Param("description") String description,
             @Param("created") Timestamp created,
             @Param("updated") Timestamp updated);
	void edit(@Param("role_id") Long role_id, @Param("name") String name, @Param("code") String code, @Param("description") String description,
              @Param("created") Timestamp created,
              @Param("updated") Timestamp updated);
	void delete(@Param("role_id") Long role_id);
	List<Role> getAllRole();
}
