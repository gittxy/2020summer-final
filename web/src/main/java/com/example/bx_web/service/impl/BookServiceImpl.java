package com.example.bx_web.service.impl;

import com.example.bx_web.mapper.BookMapper;
import com.example.bx_web.pojo.Book;
import com.example.bx_web.service.BookService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

@Service("bookService")
public class BookServiceImpl implements BookService {
    @Resource
    private BookMapper bookMapper;
    @Override
    public List<Book> booklist(int num) {
        return this.bookMapper.booklist(num);
    }

    @Override
    public List<Book> bookrandom(int user_id) { return this.bookMapper.bookrandom(user_id); }

    @Override
    public Book bookinfo(BigInteger book_id) {
        return this.bookMapper.bookinfo(book_id);
    }
    @Override
    public void add(BigInteger book_id, int book_category_id, int store_id, String name, String outline, String detail, String press,
                    String publish_date, String size, String version, String author, String translator, String isbn, String price,
                    String pages, String catalog, String market_price, String member_price, String deal_mount, String look_mount, String discount,
                    String image_url, int store_mount, Timestamp store_time, String pack_style,int is_shelf, String cname, String description,
                    String cata, String content) {
        bookMapper.add(book_id, book_category_id, store_id, name, outline, detail, press, publish_date, size, version, author, translator, isbn, price,
                pages, catalog, market_price, member_price, deal_mount, look_mount,discount, image_url, store_mount, store_time,pack_style, is_shelf, cname, description,
                cata, content);
    }

    @Override
    public void delete(BigInteger book_id) {
        bookMapper.delete(book_id);
    }

    @Override
    public List<Book> getAllBook(int store_manager_id) {
        return bookMapper.getAllBook(store_manager_id);
    }

    @Override
    public int edit(BigInteger book_id, int book_category_id, int store_id, String name, String outline, String detail, String press,
                    String publish_date, String size, String version, String author, String translator, String isbn, String price,
                    String pages, String catalog, String market_price, String member_price, String deal_mount, String look_mount, String discount,
                    String image_url, int store_mount, Timestamp store_time, String pack_style,int is_shelf, String cname, String description,
                    String cata, String content) {
        return bookMapper.edit(book_id, book_category_id, store_id, name, outline, detail, press, publish_date, size, version, author, translator, isbn, price,
                pages, catalog, market_price, member_price, deal_mount, look_mount,discount, image_url, store_mount, store_time, pack_style,is_shelf, cname, description,
                cata, content);
    }
}
