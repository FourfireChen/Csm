package com.chuansongmen.util;

import com.chuansongmen.data.bean.Order;
import com.chuansongmen.data.bean.Position;
import com.chuansongmen.data.bean.Route;
import com.chuansongmen.data.bean.Station;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Converter;

import static com.chuansongmen.data.bean.Field.COUPON_ID;
import static com.chuansongmen.data.bean.Field.FROM_LATITUDE;
import static com.chuansongmen.data.bean.Field.FROM_LONGITUDE;
import static com.chuansongmen.data.bean.Field.IS_DELAY;
import static com.chuansongmen.data.bean.Field.NEXT_ROUTE;
import static com.chuansongmen.data.bean.Field.NOW_WORKER;
import static com.chuansongmen.data.bean.Field.ORDER_CATEGORY;
import static com.chuansongmen.data.bean.Field.ORDER_ID;
import static com.chuansongmen.data.bean.Field.ORDER_PAGER_ID;
import static com.chuansongmen.data.bean.Field.ORDER_STATUS;
import static com.chuansongmen.data.bean.Field.ORDER_USER_ID;
import static com.chuansongmen.data.bean.Field.PRICE;
import static com.chuansongmen.data.bean.Field.PRICE_PROTECTION;
import static com.chuansongmen.data.bean.Field.RECEIVE_TIME;
import static com.chuansongmen.data.bean.Field.RECIPIENT_ADDRESS;
import static com.chuansongmen.data.bean.Field.RECIPIENT_NAME;
import static com.chuansongmen.data.bean.Field.RECIPIENT_PHONE;
import static com.chuansongmen.data.bean.Field.REMARK;
import static com.chuansongmen.data.bean.Field.ROUTE;
import static com.chuansongmen.data.bean.Field.STATION;
import static com.chuansongmen.data.bean.Field.TO_LATITUDE;
import static com.chuansongmen.data.bean.Field.TO_LONGTITUDE;
import static com.chuansongmen.data.bean.Field.WEIGHT;


public class OrdersConvertor implements Converter<ResponseBody, List<Order>> {
    @Override
    public List<Order> convert(ResponseBody value) throws IOException {
        List<Order> orders = new ArrayList<>();
        String string = value.string();
        JSONObject jsonObject;
        boolean isSuccess = false;
        try {
            jsonObject = new JSONObject(string);

            isSuccess = jsonObject.getString("code").equals("SUCCESS");
            if (isSuccess) {
                String ordersString = jsonObject.getString("data");
                orders.addAll(analysisOrders(ordersString));
            } else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return orders;
    }

    private List<Order> analysisOrders(String ordersString) {
        Gson gson =
                new GsonBuilder().registerTypeAdapter(Order.class, new OrderJsonAdatper()).create();
        return new ArrayList<>(Arrays.asList(gson.fromJson(ordersString, Order[].class)));

    }

    private class OrderJsonAdatper extends TypeAdapter<Order> {

        @Override
        public void write(JsonWriter out, Order value) throws IOException {
            out.beginObject();
            out.name(ORDER_ID).value(value.getOrderId());
            out.name(ORDER_PAGER_ID).value(value.getPagerId());
            out.name(ORDER_USER_ID).value(value.getUserPhone());
            out.name(FROM_LONGITUDE).value(value.getFrom().getLongitude());
            out.name(FROM_LATITUDE).value(value.getFrom().getLatitude());
            out.name(TO_LONGTITUDE).value(value.getTo().getLongitude());
            out.name(TO_LATITUDE).value(value.getTo().getLatitude());
            out.name(NOW_WORKER).value(value.getNowWoker());
            out.name(PRICE).value(value.getPrice());

            //这里给状态做了一个枚举
            out.name(ORDER_STATUS)
                    .value(Arrays.asList(Order.Status.values()).indexOf(value.getStatus()));

            out.name(RECIPIENT_NAME).value(value.getRecipientName());
            out.name(RECIPIENT_PHONE).value(value.getRecipientPhone());
            out.name(RECIPIENT_ADDRESS).value(value.getRecipientAddress());
            out.name(PRICE_PROTECTION).value(value.getPriceProtection());
            out.name(WEIGHT).value(value.getWeight());
            out.name(RECEIVE_TIME).value(value.getReceiveTime());
            out.name(ORDER_CATEGORY).value(value.getCategory());
            out.name(NEXT_ROUTE).value(value.getNextRoute());
            out.name(COUPON_ID).value(value.getCouponId());
            out.name(REMARK).value(value.getRemark());


            out.name(IS_DELAY).value(value.isDelay() ? 1 : 0);
            StringBuilder stationsStringBuilder = new StringBuilder();
            for (int i = 0; i < value.getStations().size(); i++) {
                stationsStringBuilder.append(value.getStations().get(i).getId());
                if (i != value.getStations().size() - 1) {
                    stationsStringBuilder.append(";");
                }
            }

            out.name(STATION).value(stationsStringBuilder.toString());
            StringBuilder routesStringBuilder = new StringBuilder();
            for (int i = 0; i < value.getRoutes().size(); i++) {
                stationsStringBuilder.append(value.getRoutes().get(i).getId());
                if (i != value.getRoutes().size() - 1) {
                    routesStringBuilder.append(";");
                }
            }
            out.name(ROUTE).value(routesStringBuilder.toString());

            out.endObject();
        }

        @Override
        public Order read(JsonReader in) throws IOException {
            Order order = new Order();
            Position from = new Position();
            Position to = new Position();

            in.beginObject();
            while (in.hasNext()) {
                String name = in.nextName();
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    continue;
                }
                switch (name) {
                    case ORDER_ID:
                        order.setOrderId(in.nextString());
                        break;
                    case ORDER_PAGER_ID:
                        order.setPagerId(in.nextString());
                        break;
                    case ORDER_USER_ID:
                        order.setUserPhone(in.nextString());
                        break;
                    case FROM_LONGITUDE:
                        from.setLongitude(in.nextDouble());
                        break;
                    case FROM_LATITUDE:
                        from.setLatitude(in.nextDouble());
                        break;
                    case TO_LATITUDE:
                        to.setLatitude(in.nextDouble());
                        break;
                    case TO_LONGTITUDE:
                        to.setLongitude(in.nextDouble());
                        break;
                    case NOW_WORKER:
                        order.setNowWoker(in.nextString());
                        break;
                    case PRICE:
                        order.setPrice(in.nextInt());
                        break;
                    case ORDER_STATUS:
                        //这里是一个枚举
                        order.setStatus(Order.Status.values()[in.nextInt()]);
                        break;
                    case IS_DELAY:
                        if (in.nextInt() == 1)
                            order.setDelay(true);
                        else
                            order.setDelay(false);
                        break;
                    case RECIPIENT_NAME:
                        order.setRecipientName(in.nextString());
                        break;
                    case RECIPIENT_PHONE:
                        order.setRecipientPhone(in.nextString());
                        break;
                    case RECIPIENT_ADDRESS:
                        order.setRecipientAddress(in.nextString());
                        break;
                    case PRICE_PROTECTION:
                        order.setPriceProtection(in.nextInt());
                        break;
                    case WEIGHT:
                        order.setWeight(in.nextInt());
                        break;
                    case RECEIVE_TIME:
                        order.setReceiveTime(in.nextString());
                        break;
                    case ORDER_CATEGORY:
                        order.setCategory(in.nextInt());
                        break;
                    case STATION:
                        String[] stationsString = in.nextString().split(";");
                        order.setStations(new ArrayList<Station>());
                        for (String stationString :
                                stationsString) {
                            order.getStations().add(new Station(stationString));
                        }
                        break;
                    case ROUTE:
                        String[] routesString = in.nextString().split(";");
                        order.setStations(new ArrayList<Station>());
                        for (String routeString :
                                routesString) {
                            order.getRoutes().add(new Route(routeString));
                        }
                        break;
                    case NEXT_ROUTE:
                        order.setNextRoute(in.nextString());
                        break;
                    case COUPON_ID:
                        order.setCouponId(in.nextString());
                        break;
                    case REMARK:
                        order.setRemark(in.nextString());
                        break;
                }
            }
            in.endObject();
            order.setFrom(from);
            order.setTo(to);
            return order;
        }
    }
}
