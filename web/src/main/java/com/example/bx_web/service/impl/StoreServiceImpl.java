package com.example.bx_web.service.impl;

import com.example.bx_web.mapper.StoreMapper;
import com.example.bx_web.pojo.Store;
import com.example.bx_web.service.StoreService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

@Service("storeService")
public class StoreServiceImpl implements StoreService {
    @Resource
    private StoreMapper storeMapper;
    @Override
    public void edit(int store_id, int store_manager_id, String store_phone_num,String store_telephone,
                     String store_name,String store_position, Timestamp created, Timestamp updated) {
        storeMapper.edit(store_id,store_manager_id,store_phone_num,store_telephone,store_name,store_position,created,updated);
    }

    @Override
    public Store getStore(int store_manager_id) {
        return storeMapper.getStore(store_manager_id);
    }

}
