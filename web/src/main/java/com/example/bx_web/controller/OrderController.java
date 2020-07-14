package com.example.bx_web.controller;

import com.example.bx_web.pojo.Order;
import com.example.bx_web.service.*;
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
public class OrderController {
    @Autowired
    OrderService orderService;

    @RequestMapping(value = "/order")
    public List<Order> Order(Order order) throws JSONException {
        List<Order> orderlist =orderService.OrderR(order.getUser_id());
        return orderlist;
    }
    @RequestMapping(value = "/addorder")
    public String AddOrder(@RequestBody String jsonStr) throws JSONException {
        System.out.println(jsonStr);
        Gson gson=new Gson();
        Type type = new TypeToken<List<Order>>(){}.getType();
        List<Order> list = gson.fromJson(jsonStr, type);
        for (int i = 0; i < list.size(); i++) {
            orderService.addorder(list.get(i).getUser_id(),list.get(i).getPayment(),list.get(i).getStatus(),list.get(i).getPayment_type());
        }
        return JsonUtils.putJson("success");
    }
    @RequestMapping(value = "/deleteorder")
    public String DeleteOrder(@RequestBody String jsonStr) throws JSONException {
        Gson gson=new Gson();
        Type type = new TypeToken<List<Order>>(){}.getType();
        List<Order> list = gson.fromJson(jsonStr, type);
        for (int i = 0; i < list.size(); i++) {
            orderService.deleteorder(list.get(i).getOrder_id());
        }
        return JsonUtils.putJson("success");
    }
    @RequestMapping(value = "/editorder")
    public String EditOrder(Order order) throws JSONException {
        orderService.editorder(order.getOrder_id(),order.getStatus());
        return JsonUtils.putJson("success");
    }

}
