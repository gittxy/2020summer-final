package com.example.bx_web.controller;

import com.example.bx_web.pojo.Cart;
import com.example.bx_web.pojo.Favorite;
import com.example.bx_web.service.FavoriteService;
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
public class FavoriteController {
    @Autowired
    public FavoriteService favoriteService;

    @RequestMapping(value = "/favorite")
    public List<Favorite> Favorite(Favorite favorite) throws JSONException {
        List<Favorite> favoriter=favoriteService.findList(favorite.getUser_id());
        return favoriter;
    }

    @RequestMapping(value = "/addfavorite")
    public String AddFavorite(Favorite favorite) throws JSONException {
        System.out.println(favorite.getUser_id());
        favoriteService.add(favorite.getUser_id(),favorite.getBook_id(),favorite.getStore_id(),
                favorite.getName(),favorite.getPrice());
        return JsonUtils.putJson("success");
    }

    @RequestMapping(value = "/deletefavorite")
    public String DeleteFavorite(@RequestBody String jsonStr) throws JSONException {
        Gson gson=new Gson();
        Type type = new TypeToken<List<Favorite>>(){}.getType();
        List<Favorite> list = gson.fromJson(jsonStr, type);
        for (int i = 0; i < list.size(); i++) {
            favoriteService.delete(list.get(i).getFavorite_id());
        }
        return JsonUtils.putJson("success");
    }
}
