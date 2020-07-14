package com.example.bx_web.mapper;

import com.example.bx_web.pojo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface OrderMapper {
    List<Order> OrderR(int user_id);
    void addorder(@Param("user_id") int user_id,@Param("payment") String payment,@Param("status") int status,
                  @Param("payment_type") int payment_type);
    void editorder(@Param("order_id") BigInteger order_id,@Param("status") int status);
    void deleteorder(BigInteger order_id);
}