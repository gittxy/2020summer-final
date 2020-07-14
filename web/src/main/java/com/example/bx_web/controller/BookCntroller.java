package com.example.bx_web.controller;

import com.example.bx_web.pojo.Book;
import com.example.bx_web.pojo.Store;
import com.example.bx_web.service.BookService;
import com.example.bx_web.service.StoreService;
import com.example.bx_web.utils.JsonUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.util.List;


@RestController
public class BookCntroller {
    @Autowired
    public BookService bookService;

    @Autowired
    public StoreService storeService;

    @RequestMapping(value = "/bookinfo")
    public String BookInfo(Book book) throws JSONException {
        Book book2=bookService.bookinfo(book.getBook_id());
        return JsonUtils.putJson(book2);
    }

    @RequestMapping(value = "/booklist")
    public List<Book> BookList(Book booka) throws JSONException {
        System.out.println(JsonUtils.putJson(booka));
        System.out.println(booka.getUser_id());
        List<Book> book=bookService.bookrandom(booka.getUser_id());
        if(book.size() == 0){
            book = bookService.booklist(5);
        }
        else if(book.size()<5&&book.size()>0){
            System.out.println(book.get(0).getBook_id());
            List<Book> book2=bookService.booklist(5-book.size());
            book.addAll(book2);
        }
        return book;
    }

    @RequestMapping(value = "/addBook")
    public String AddBook(@RequestBody String jsonStr) throws JSONException {
        System.out.println(jsonStr);
        Gson gson = new Gson();
        Book book = gson.fromJson(jsonStr, Book.class);
        bookService.add(book.getBook_id(),book.getBook_category_id(),book.getStore_id(),book.getName(),book.getOutline(),
                book.getDetail(),book.getPress(),book.getPublish_date(),book.getSize(),book.getVersion(),book.getAuthor(),
                book.getTranslator(),book.getIsbn(),book.getPrice(),book.getPages(),book.getCatalog(),book.getMarket_price(),
                book.getMember_price(),book.getDeal_mount(),book.getLook_mount(),book.getDiscount(),book.getImage_url(),
                book.getStore_mount(),book.getStore_time(),book.getPack_style(),book.getIs_shelf(),book.getcname(),book.getDescription(),
                book.getCata(),book.getContent());
        return JsonUtils.putJson("success");
    }

    @RequestMapping(value = "/deleteBook")
    public String DeleteBook(@RequestBody String jsonStr) throws JSONException {
        Gson gson=new Gson();
        Type type = new TypeToken<List<Book>>(){}.getType();
        List<Book> list = gson.fromJson(jsonStr, type);
        for (int i = 0; i < list.size(); i++) {
            bookService.delete(list.get(i).getBook_id());
        }
        return JsonUtils.putJson("success");

    }

    @RequestMapping(value = "/editBook")
    public String EditBook(@RequestBody String jsonStr) throws JSONException {
        Gson gson = new Gson();
        Book book = gson.fromJson(jsonStr, Book.class);
        bookService.edit(book.getBook_id(),book.getBook_category_id(),book.getStore_id(),book.getName(),book.getOutline(),
                book.getDetail(),book.getPress(),book.getPublish_date(),book.getSize(),book.getVersion(),book.getAuthor(),
                book.getTranslator(),book.getIsbn(),book.getPrice(),book.getPages(),book.getCatalog(),book.getMarket_price(),
                book.getMember_price(),book.getDeal_mount(),book.getLook_mount(),book.getDiscount(),book.getImage_url(),
                book.getStore_mount(),book.getStore_time(),book.getPack_style(),book.getIs_shelf(),book.getcname(),book.getDescription(),
                book.getCata(),book.getContent());
        return JsonUtils.putJson("success");
    }

    @RequestMapping(value = "/Book")
    public List<Book> GetTotalBook(@RequestBody String jsonStr) throws JSONException {
        Gson gson = new Gson();
        Store store = gson.fromJson(jsonStr, Store.class);
        List<Book> list = bookService.getAllBook(store.getStore_manager_id());

        return list;
    }
}
