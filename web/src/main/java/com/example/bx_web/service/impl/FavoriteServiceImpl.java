package com.example.bx_web.service.impl;

import com.example.bx_web.mapper.FavoriteMapper;
import com.example.bx_web.pojo.Favorite;
import com.example.bx_web.service.FavoriteService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
@Service("favoriteService")
public class FavoriteServiceImpl implements FavoriteService {
    @Resource
    private FavoriteMapper favoriteMapper;

    @Override
    public void add(int user_id, BigInteger book_id, int store_id, String name, BigDecimal price) {
        this.favoriteMapper.add(user_id,book_id,store_id,name,price);
    }

    @Override
    public void delete(int favorite_id) {
        this.favoriteMapper.delete(favorite_id);
    }

    @Override
    public List<Favorite> findList(int user_id) {
        return this.favoriteMapper.findList(user_id);
    }
}
