package com.example.bx_web.mapper;

import com.example.bx_web.pojo.Store;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

public interface StoreMapper {
    void edit(@Param("store_id") int store_id, @Param("store_manager_id") int store_manager_id,
              @Param("store_phone_num") String store_phone_num, @Param("store_telephone") String store_telephone,
              @Param("store_name") String store_name, @Param("store_position") String store_position,
              @Param("created") Timestamp created,
              @Param("updated") Timestamp updated);
    Store getStore(@Param("store_manager_id") int store_manager_id);
}
