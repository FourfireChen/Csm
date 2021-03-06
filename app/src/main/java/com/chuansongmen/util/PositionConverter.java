package com.chuansongmen.util;

import com.chuansongmen.data.bean.Position;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

import static com.chuansongmen.common.Field.LATITUDE;
import static com.chuansongmen.common.Field.LONGITUDE;

public class PositionConverter implements Converter<ResponseBody, Position> {
    @Override
    public Position convert(ResponseBody value) throws IOException {
        String body = value.string();
        try {
            JSONObject bodyJson = new JSONObject(body);
            if (bodyJson.getInt("code") == 200) {
                JSONObject dataJson = new JSONObject(bodyJson.getString("data"));
                return new Position(dataJson.getDouble(LONGITUDE), dataJson.getDouble(LATITUDE));
            } else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
