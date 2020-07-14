package com.example.bx_web.service;

import com.example.bx_web.pojo.Store;

import java.sql.Timestamp;

public interface StoreService {
    void edit(int store_id, int store_manager_id, String store_phone_num, String store_telephone,
              String store_name, String store_position, Timestamp created, Timestamp updated);
    Store getStore(int store_manager_id);
}
