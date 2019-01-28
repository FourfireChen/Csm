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

import static com.chuansongmen.common.Field.ARRIVE_STATION_TIME;
import static com.chuansongmen.common.Field.COLLECT_FROM_STATION_TIME;
import static com.chuansongmen.common.Field.COLLECT_FROM_USER_TIME;
import static com.chuansongmen.common.Field.COMPLETE_TIME;
import static com.chuansongmen.common.Field.COUPON_ID;
import static com.chuansongmen.common.Field.DELAY_TIME;
import static com.chuansongmen.common.Field.FROM_LATITUDE;
import static com.chuansongmen.common.Field.FROM_LONGITUDE;
import static com.chuansongmen.common.Field.IS_DELAY;
import static com.chuansongmen.common.Field.IS_ORDER_IN_CAINIAO;
import static com.chuansongmen.common.Field.MESSAGE_STR;
import static com.chuansongmen.common.Field.NEXT_ROUTE;
import static com.chuansongmen.common.Field.NOW_WORKER;
import static com.chuansongmen.common.Field.ORDER_CATEGORY;
import static com.chuansongmen.common.Field.ORDER_ID;
import static com.chuansongmen.common.Field.ORDER_PAGER_ID;
import static com.chuansongmen.common.Field.ORDER_STATUS;
import static com.chuansongmen.common.Field.ORDER_USER_ADDRESS;
import static com.chuansongmen.common.Field.ORDER_USER_ID;
import static com.chuansongmen.common.Field.ORDER_USER_NAME;
import static com.chuansongmen.common.Field.PRICE;
import static com.chuansongmen.common.Field.PRICE_PROTECTION;
import static com.chuansongmen.common.Field.RECIPIENT_ADDRESS;
import static com.chuansongmen.common.Field.RECIPIENT_NAME;
import static com.chuansongmen.common.Field.RECIPIENT_PHONENUMBER;
import static com.chuansongmen.common.Field.REMARK;
import static com.chuansongmen.common.Field.ROUTE;
import static com.chuansongmen.common.Field.START_TIME;
import static com.chuansongmen.common.Field.STATION;
import static com.chuansongmen.common.Field.TO_LATITUDE;
import static com.chuansongmen.common.Field.TO_LONGITUDE;
import static com.chuansongmen.common.Field.WEIGHT;


public class OrdersConvertor implements Converter<ResponseBody, List<Order>> {
    @Override
    public List<Order> convert(ResponseBody value) throws IOException {
        List<Order> orders = new ArrayList<>();
        String string = value.string();
        JSONObject jsonObject;
        boolean isSuccess;
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
            out.name(ORDER_USER_ID).value(value.getOrderUserId());
            out.name(ORDER_USER_NAME).value(value.getUserName());
            out.name(ORDER_USER_ADDRESS).value(value.getUserAddress());
            out.name(FROM_LONGITUDE).value(value.getFrom().getLongitude());
            out.name(FROM_LATITUDE).value(value.getFrom().getLatitude());
            out.name(IS_ORDER_IN_CAINIAO).value(value.isInCainiao() ? 1 : 0);
            out.name(TO_LONGITUDE).value(value.getTo().getLongitude());
            out.name(TO_LATITUDE).value(value.getTo().getLatitude());
            out.name(NOW_WORKER).value(value.getNowWorker());
            out.name(PRICE).value(value.getPrice());
            //这里给状态做了一个枚举
            out.name(ORDER_STATUS)
                    .value(Arrays.asList(Order.Status.values()).indexOf(value.getStatus()));
            out.name(IS_DELAY).value(value.isDelay() ? 1 : 0);
            out.name(RECIPIENT_NAME).value(value.getRecipientName());
            out.name(RECIPIENT_PHONENUMBER).value(value.getRecipientPhone());
            out.name(RECIPIENT_ADDRESS).value(value.getRecipientAddress());
            out.name(PRICE_PROTECTION).value(value.getPriceProtection());
            out.name(WEIGHT).value(value.getWeight());
            out.name(START_TIME).value(value.getStartTime());
            out.name(COMPLETE_TIME).value(value.getCompleteTime());
            out.name(ORDER_CATEGORY).value(value.getCategory());

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

            out.name(NEXT_ROUTE).value(value.getNextRoute());
            out.name(COUPON_ID).value(value.getCouponId());
            out.name(REMARK).value(value.getRemark());
            out.name(ARRIVE_STATION_TIME).value(value.getArriveStationTime());
            out.name(COLLECT_FROM_USER_TIME).value(value.getCollectFromUserTime());
            out.name(COLLECT_FROM_STATION_TIME).value(value.getCollectFromStationTime());
            out.name(MESSAGE_STR).value(value.getMessageStr());
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
                        order.setOrderUserId(in.nextString());
                        break;
                    case ORDER_USER_NAME:
                        order.setUserName(in.nextString());
                        break;
                    case ORDER_USER_ADDRESS:
                        order.setUserAddress(in.nextString());
                        break;
                    case FROM_LONGITUDE:
                        from.setLongitude(in.nextDouble());
                        break;
                    case FROM_LATITUDE:
                        from.setLatitude(in.nextDouble());
                        break;
                    case IS_ORDER_IN_CAINIAO:
                        order.setInCainiao(in.nextInt() == 1);
                        break;
                    case TO_LATITUDE:
                        to.setLatitude(in.nextDouble());
                        break;
                    case TO_LONGITUDE:
                        to.setLongitude(in.nextDouble());
                        break;
                    case NOW_WORKER:
                        order.setNowWorker(in.nextString());
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
                    case DELAY_TIME:
                        order.setDelayTime(in.nextString());
                        break;
                    case RECIPIENT_NAME:
                        order.setRecipientName(in.nextString());
                        break;
                    case RECIPIENT_PHONENUMBER:
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
                    case START_TIME:
                        order.setStartTime(in.nextString());
                        break;
                    case COMPLETE_TIME:
                        order.setCompleteTime(in.nextString());
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
                    case ARRIVE_STATION_TIME:
                        order.setArriveStationTime(in.nextString());
                        break;
                    case COLLECT_FROM_USER_TIME:
                        order.setCollectFromUserTime(in.nextString());
                        break;
                    case COLLECT_FROM_STATION_TIME:
                        order.setCollectFromStationTime(in.nextString());
                        break;
                    case MESSAGE_STR:
                        order.setMessageStr(in.nextString());
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
