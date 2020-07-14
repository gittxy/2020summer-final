package com.example.bx_web.mapper;



import com.example.bx_web.pojo.Favorite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
@Mapper
public interface FavoriteMapper {
    void add(@Param("user_id") int user_id,@Param("book_id") BigInteger book_id,@Param("store_id") int store_id,
             @Param("name") String name,@Param("price") BigDecimal price);
    void delete(int favorite_id);
    List<Favorite> findList(int user_id);
}
