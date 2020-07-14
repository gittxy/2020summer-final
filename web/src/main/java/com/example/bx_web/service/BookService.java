package com.example.bx_web.service;

import com.example.bx_web.pojo.Book;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

public interface BookService {
    List<Book> booklist(int num);
    List<Book> bookrandom(int user_id);
    Book bookinfo(BigInteger book_id);
    void add(BigInteger book_id, int book_category_id, int store_id, String name, String outline, String detail, String press,
             String publish_date, String size, String version, String author, String translator, String isbn, String price,
             String pages, String catalog, String market_price, String member_price, String deal_mount, String look_mount, String discount,
             String image_url, int store_mount, Timestamp store_time, String pack_style,int is_shelf, String canme, String description,
             String cata, String content);
    void delete(BigInteger book_id);
    List<Book> getAllBook(int store_manager_id);
    int edit(BigInteger book_id, int book_category_id, int store_id, String name, String outline, String detail, String press,
             String publish_date, String size, String version, String author, String translator, String isbn, String price,
             String pages, String catalog, String market_price, String member_price, String deal_mount, String look_mount, String discount,
             String image_url, int store_mount, Timestamp store_time, String pack_style,int is_shelf, String canme, String description,
             String cata, String content);
}
