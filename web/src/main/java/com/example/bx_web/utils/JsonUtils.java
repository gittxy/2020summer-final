package com.example.bx_web.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JsonUtils {


        public static String putJson(Object object){
            ObjectMapper mapper=new ObjectMapper();
            try{
                return mapper.writeValueAsString(object);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return null;
        }

}
