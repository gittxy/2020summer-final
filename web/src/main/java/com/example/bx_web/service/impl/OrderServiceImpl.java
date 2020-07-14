package com.example.bx_web.service.impl;

import com.example.bx_web.mapper.OrderMapper;
import com.example.bx_web.pojo.Order;
import com.example.bx_web.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper orderMapper;


    @Override
    public List<Order> OrderR(int user_id) {
        return this.orderMapper.OrderR(user_id);
    }

    @Override
    public void addorder(int user_id, String payment, int status, int payment_type) {
        this.orderMapper.addorder(user_id,payment,status,payment_type);
    }

    @Override
    public void editorder(BigInteger order_id, int status) {
        this.orderMapper.editorder(order_id,status);
    }

    @Override
    public void deleteorder(BigInteger order_id) {
        this.orderMapper.deleteorder(order_id);
    }

}