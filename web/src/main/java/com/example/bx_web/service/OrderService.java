
package com.example.bx_web.service;

import com.example.bx_web.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.util.List;

public interface OrderService {
    List<Order> OrderR(int user_id);
    void addorder(int user_id,String payment,int status, int payment_type);
    void editorder(BigInteger order_id, int status);
    void deleteorder(BigInteger order_id);
}