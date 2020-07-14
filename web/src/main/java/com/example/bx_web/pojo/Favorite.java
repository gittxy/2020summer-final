package com.example.bx_web.pojo;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Favorite {
    private int favorite_id;
    private int user_id;
    private BigInteger book_id;
    private int store_id;
    private String name;
    private String image_url;
    private BigDecimal price;

    public Favorite() {
    }

    public Favorite(int user_id) {
        this.user_id = user_id;
    }

    public Favorite(int user_id, BigInteger book_id, int store_id, String name, String image_url, BigDecimal price) {
        this.user_id = user_id;
        this.book_id = book_id;
        this.store_id = store_id;
        this.name = name;
        this.image_url = image_url;
        this.price = price;
    }

    public Favorite(int favorite_id, int user_id, BigInteger book_id, int store_id, String name, String image_url, BigDecimal price) {
        this.favorite_id = favorite_id;
        this.user_id = user_id;
        this.book_id = book_id;
        this.store_id = store_id;
        this.name = name;
        this.image_url = image_url;
        this.price = price;
    }


    public int getFavorite_id() {
        return favorite_id;
    }

    public void setFavorite_id(int favorite_id) {
        this.favorite_id = favorite_id;
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
}
