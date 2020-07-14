package com.example.bx_web.mapper;

import com.example.bx_web.pojo.Cart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Mapper
public interface CartMapper {

    void add(@Param("user_id") int user_id,@Param("book_id") BigInteger book_id,@Param("store_id") int store_id,
             @Param("name") String name,@Param("price") BigDecimal price, @Param("num") int num,
             @Param("money") BigDecimal money);
    void edit(@Param("cart_id") int cart_id,@Param("num") int num,@Param("money") BigDecimal money);
    void delete(int cart_id);
    List<Cart> findList(int user_id);
    BigDecimal getTotal(int cart_id);

}
