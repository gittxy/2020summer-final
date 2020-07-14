package com.example.bx_web.service.impl;

import com.example.bx_web.mapper.CartMapper;
import com.example.bx_web.pojo.Cart;
import com.example.bx_web.service.CartService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
@Service("cartService")
public class CartServiceImpl implements CartService {
    @Resource
    private CartMapper cartMapper;


    @Override
    public void add(int user_id, BigInteger book_id, int store_id, String name, BigDecimal price, int num, BigDecimal money) {
        System.out.println("sdsdf" + user_id);
        this.cartMapper.add(user_id,book_id,store_id,name,price,num,money);
    }

    @Override
    public void edit(int cart_id,int num, BigDecimal money) { this.cartMapper.edit(cart_id,num,money); }

    @Override
    public void delete(int cart_id) {
        this.cartMapper.delete(cart_id);
    }

    @Override
    public List<Cart> findList(int user_id) {
        return this.cartMapper.findList(user_id);
    }

    @Override
    public BigDecimal getTotal(int cart_id) {
        return this.cartMapper.getTotal(cart_id);
    }
}
