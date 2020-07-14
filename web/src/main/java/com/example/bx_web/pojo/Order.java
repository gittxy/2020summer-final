package com.example.bx_web.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigInteger;
import java.sql.Timestamp;

@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
public class Order {
    private BigInteger order_id;
    private int user_id;
    private String payment;
    private int status;
    private int payment_type;
    private String post_fee;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp create_time;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp update_time;
    private int order_mount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp payment_time;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp end_time;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp close_time;
    private String shipping_name;
    private String shipping_code;
    private String buyer_message;
    private int buyer_rate;

    public Order() {
    }

    public Order(int user_id) {
        this.user_id = user_id;
    }

    public Order(BigInteger order_id) {
        this.order_id = order_id;
    }

    public Order(BigInteger order_id, int user_id, String payment, int status, int payment_type, String post_fee, Timestamp create_time, Timestamp update_time, int order_mount, Timestamp payment_time, Timestamp end_time, Timestamp close_time, String shipping_name, String shipping_code, String buyer_message, int buyer_rate) {
        this.order_id = order_id;
        this.user_id = user_id;
        this.payment = payment;
        this.status = status;
        this.payment_type = payment_type;
        this.post_fee = post_fee;
        this.create_time = create_time;
        this.update_time = update_time;
        this.order_mount = order_mount;
        this.payment_time = payment_time;
        this.end_time = end_time;
        this.close_time = close_time;
        this.shipping_name = shipping_name;
        this.shipping_code = shipping_code;
        this.buyer_message = buyer_message;
        this.buyer_rate = buyer_rate;
    }

    public BigInteger getOrder_id() {
        return order_id;
    }

    public void setOrder_id(BigInteger order_id) {
        this.order_id = order_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(int payment_type) {
        this.payment_type = payment_type;
    }

    public String getPost_fee() {
        return post_fee;
    }

    public void setPost_fee(String post_fee) {
        this.post_fee = post_fee;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    public Timestamp getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Timestamp update_time) {
        this.update_time = update_time;
    }

    public int getOrder_mount() {
        return order_mount;
    }

    public void setOrder_mount(int order_mount) {
        this.order_mount = order_mount;
    }

    public Timestamp getPayment_time() {
        return payment_time;
    }

    public void setPayment_time(Timestamp payment_time) {
        this.payment_time = payment_time;
    }

    public Timestamp getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Timestamp end_time) {
        this.end_time = end_time;
    }

    public Timestamp getClose_time() {
        return close_time;
    }

    public void setClose_time(Timestamp close_time) {
        this.close_time = close_time;
    }

    public String getShipping_name() {
        return shipping_name;
    }

    public void setShipping_name(String shipping_name) {
        this.shipping_name = shipping_name;
    }

    public String getShipping_code() {
        return shipping_code;
    }

    public void setShipping_code(String shipping_code) {
        this.shipping_code = shipping_code;
    }

    public String getBuyer_message() {
        return buyer_message;
    }

    public void setBuyer_message(String buyer_message) {
        this.buyer_message = buyer_message;
    }

    public int getBuyer_rate() {
        return buyer_rate;
    }

    public void setBuyer_rate(int buyer_rate) {
        this.buyer_rate = buyer_rate;
    }
}