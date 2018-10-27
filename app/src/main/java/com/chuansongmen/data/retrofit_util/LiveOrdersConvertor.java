package com.chuansongmen.data.retrofit_util;

import com.chuansongmen.data.bean.Order;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class LiveOrdersConvertor implements Converter<ResponseBody, List<Order>> {
    @Override
    public List<Order> convert(ResponseBody value) throws IOException {
        List<Order> orders = new ArrayList<>();
        String string = value.string();
        JSONObject jsonObject = null;
        boolean isSuccess = false;
        try {
            jsonObject = new JSONObject(string);

            isSuccess = jsonObject.getString("code").equals("200");
            if (isSuccess){
                String ordersString = jsonObject.getString("data");

            }else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return orders;
    }

    //private List<>
}
