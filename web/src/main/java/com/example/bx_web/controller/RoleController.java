package com.example.bx_web.controller;

import com.example.bx_web.pojo.Cart;
import com.example.bx_web.pojo.Role;
import com.example.bx_web.service.RoleService;
import com.example.bx_web.utils.JsonUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.util.List;

@RestController
public class RoleController {
    @Autowired
    public RoleService roleService;

    @RequestMapping(value = "/addRole")
    public String AddRole(@RequestBody String jsonStr) throws JSONException {
        Gson gson = new Gson();
        Role role = gson.fromJson(jsonStr, Role.class);
        roleService.add(role.getRole_id(),role.getName(),role.getCode(),role.getDescription(),role.getCreated(),role.getUpdated());
        return JsonUtils.putJson("success");
    }

    @RequestMapping(value = "/deleteRole")
    public String DeleteRole(@RequestBody String jsonStr) throws JSONException {
        Gson gson=new Gson();
        Type type = new TypeToken<List<Role>>(){}.getType();
        List<Role> list = gson.fromJson(jsonStr, type);
        for (int i = 0; i < list.size(); i++) {
            roleService.delete(list.get(i).getRole_id());
        }
        return JsonUtils.putJson("success");
    }

    @RequestMapping(value = "/editRole")
    public String EditRole(@RequestBody String jsonStr) throws JSONException {
        Gson gson = new Gson();
        Role role = gson.fromJson(jsonStr, Role.class);
        roleService.edit(role.getRole_id(),role.getName(),role.getCode(),role.getDescription(),role.getCreated(),role.getUpdated());
        return JsonUtils.putJson("success");
    }

    @RequestMapping(value = "/role")
    public List<Role> GetTotalRole() throws JSONException {
        List<Role> list = roleService.getAllRole();

        return list;
    }

}
