package com.example.bx_web.pojo;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Cart {
    private int cart_id;
    private int user_id;
    private BigInteger book_id;
    private int store_id;
    private String name;
    private String image_url;
    private BigDecimal price;
    private int num;
    private BigDecimal money;

    public Cart(int user_id) {this.user_id = user_id;}


    public Cart() {
    }

    public Cart(int cart_id, int user_id, BigInteger book_id, int store_id, String name, String image_url, BigDecimal price, int num, BigDecimal money) {
        this.cart_id = cart_id;
        this.user_id = user_id;
        this.book_id = book_id;
        this.store_id = store_id;
        this.name = name;
        this.image_url = image_url;
        this.price = price;
        this.num = num;
        this.money = money;
    }

    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public BigInteger getBook_id() {
        return book_id;
    }

    public void setBook_id(BigInteger book_id) {
        this.book_id = book_id;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
