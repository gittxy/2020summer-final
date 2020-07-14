package com.example.bx_web.service;

import com.example.bx_web.pojo.Favorite;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public interface FavoriteService {
    void add(int user_id, BigInteger book_id, int store_id, String name, BigDecimal price);
    void delete(int favorite_id);
    List<Favorite> findList(int user_id);
}
