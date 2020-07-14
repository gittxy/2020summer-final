package com.example.bx_web.service;

import com.example.bx_web.pojo.Cart;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public interface CartService {
    void add(int user_id,BigInteger book_id,int store_id,String name,BigDecimal price,int num,BigDecimal money);
    void edit(int cart_id,int num,BigDecimal money);
    void delete(int cart_id);
    List<Cart> findList(int user_id);
    BigDecimal getTotal(int cart_id);

}
