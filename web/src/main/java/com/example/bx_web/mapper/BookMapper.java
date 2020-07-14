package com.example.bx_web.mapper;

import com.example.bx_web.pojo.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface BookMapper {
    List<Book> booklist(int num);
    List<Book> bookrandom(int user_id);
    Book bookinfo(BigInteger book_id);
    void add(@Param("book_id") BigInteger book_id, @Param("book_category_id") int book_category_id, @Param("store_id") int store_id,
             @Param("name") String name, @Param("outline") String outline, @Param("detail") String detail,
             @Param("press") String press, @Param("publish_date") String publish_date, @Param("size") String size,
             @Param("version") String version, @Param("author") String author, @Param("translator") String translator,
             @Param("isbn") String isbn, @Param("price") String price, @Param("pages") String pages,
             @Param("catalog") String catalog, @Param("market_price") String market_price, @Param("member_price") String member_price,
             @Param("deal_mount") String deal_mount, @Param("look_mount") String look_mount, @Param("discount") String discount,
             @Param("image_url") String image_url, @Param("store_mount") int store_mount,@Param("store_time") Timestamp store_time,
             @Param("pack_style") String pack_style,
             @Param("is_shelf") int is_shelf, @Param("cname") String cname, @Param("description") String description,
             @Param("cata") String cata, @Param("content") String content
    );
    void delete(@Param("book_id") BigInteger book_id);
    List<Book> getAllBook(@Param("store_manager_id") int store_manager_id);
    int edit(@Param("book_id") BigInteger book_id, @Param("book_category_id") int book_category_id, @Param("store_id") int store_id,
             @Param("name") String name,@Param("outline") String outline,@Param("detail") String detail,
             @Param("press") String press,@Param("publish_date") String publish_date,@Param("size") String size,
             @Param("version") String version,@Param("author") String author,@Param("translator") String translator,
             @Param("isbn") String isbn,@Param("price") String price,@Param("pages") String pages,
             @Param("catalog") String catalog,@Param("market_price") String market_price,@Param("member_price") String member_price,
             @Param("deal_mount") String deal_mount,@Param("look_mount") String look_mount,@Param("discount") String discount,
             @Param("image_url") String image_url,@Param("store_mount") int store_mount,@Param("store_time") Timestamp store_time,
             @Param("pack_style") String pack_style,
             @Param("is_shelf") int is_shelf,@Param("cname") String cname,@Param("description") String description,
             @Param("cata") String cata,@Param("content") String content);
}
