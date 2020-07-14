package com.example.bx_web.controller;

import com.example.bx_web.pojo.Cart;
import com.example.bx_web.pojo.Total;
import com.example.bx_web.pojo.User;
import com.example.bx_web.service.CartService;
import com.example.bx_web.service.UserService;
import com.example.bx_web.utils.JsonUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;

@RestController
public class CartController {
    @Autowired
    public CartService cartService;
    @Autowired
    public UserService userService;

    @RequestMapping(value = "/cart")
    public List<Cart> Cart(Cart cart) throws JSONException {
        List<Cart> cartr=cartService.findList(cart.getUser_id());
        return cartr;
    }

    @RequestMapping(value = "/addcart")
    public String AddCart(Cart cart) throws JSONException {
        System.out.println(JsonUtils.putJson(cart));
        cartService.add(cart.getUser_id(),cart.getBook_id(),cart.getStore_id(),cart.getName(),cart.getPrice(),1,cart.getPrice());
        return JsonUtils.putJson("success");
    }

    @RequestMapping(value = "/deletecart")
    public String DeleteCart(@RequestBody String jsonStr) throws JSONException {
        Gson gson=new Gson();
        Type type = new TypeToken<List<Cart>>(){}.getType();
        List<Cart> list = gson.fromJson(jsonStr, type);
        for (int i = 0; i < list.size(); i++) {
            cartService.delete(list.get(i).getCart_id());
        }
        return JsonUtils.putJson("success");
}

    @RequestMapping(value = "/editcart")
    public String EditCart(@RequestBody String jsonStr) throws JSONException {
        Gson gson = new Gson();
        Cart cart = gson.fromJson(jsonStr, Cart.class);
        cartService.edit(cart.getCart_id(),cart.getNum(),cart.getPrice().multiply(BigDecimal.valueOf(cart.getNum())));
        return JsonUtils.putJson("success");
    }

    @RequestMapping(value = "/gettotalcart")
    public Total GettotalCart(@RequestBody String jsonStr) throws JSONException {
        Gson gson=new Gson();
        Type type = new TypeToken<List<Cart>>(){}.getType();
        List<Cart> list = gson.fromJson(jsonStr, type);
        BigDecimal total=new BigDecimal(0);
        for (int i = 0; i < list.size(); i++) {
            total=total.add(cartService.getTotal(list.get(i).getCart_id()));
        }
        int id=list.get(0).getUser_id();
        User user=userService.userinfo(id);
        if(user.getActive()==1){
            total=total.multiply(BigDecimal.valueOf(0.9));
        }
        user.setPoints(total.intValue());
        userService.points(user.getUser_id(),user.getPoints());
        Total t =new Total();
        t.setTotal(total);
        return t;
    }
}
