package com.example.bx_web.controller;

import com.example.bx_web.pojo.Role;
import com.example.bx_web.pojo.Store;
import com.example.bx_web.service.StoreService;
import com.example.bx_web.utils.JsonUtils;
import com.google.gson.Gson;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class StoreController {
    @Autowired
    public StoreService storeService;

    @RequestMapping(value = "/editStore")
    public String EditStore(Store store) throws JSONException {
        storeService.edit(store.getStore_id(),store.getStore_manager_id(),store.getStore_phone_num(),store.getStore_telephone(),store.getStore_name(),
                store.getStore_position(),store.getCreated(),store.getUpdated());
        return JsonUtils.putJson("success");

    }

    @RequestMapping(value = "/store")
    public Store GetStore(Store store) throws JSONException {
        return storeService.getStore(store.getStore_manager_id());
    }

}
