package com.chuansongmen.util;

import com.chuansongmen.data.bean.Route;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Converter;

import static com.chuansongmen.data.bean.Field.ROUTE_ID;
import static com.chuansongmen.data.bean.Field.ROUTE_WORKER_ID;
import static com.chuansongmen.data.bean.Field.ROUTE_WORKER_NAME;

public class RoutesConvertor implements Converter<ResponseBody, List<Route>> {
    @Override
    public List<Route> convert(ResponseBody value) throws IOException {
        List<Route> routes = new ArrayList<>();
        String body = value.string();
        try {
            JSONObject jsonObject = new JSONObject(body);
            if (jsonObject.getString("code").equals("200")) {
                String routesString = jsonObject.getString("data");
                routes.addAll(analysisRoutes(routesString));
            } else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return routes;
    }

    private List<Route> analysisRoutes(String routesString) throws JSONException {
        JSONArray array = new JSONArray(routesString);
        List<Route> routes = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            Route route = new Route();
            route.setId(object.getString(ROUTE_ID));
            route.setWorkerId(object.getInt(ROUTE_WORKER_ID));
            route.setWorkerName(object.getString(ROUTE_WORKER_NAME));
            routes.add(route);
        }
        return routes;
    }
}
