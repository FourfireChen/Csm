package com.chuansongmen.util;

import com.chuansongmen.data.bean.Position;
import com.chuansongmen.data.bean.Station;
import com.chuansongmen.data.bean.Worker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

import static com.chuansongmen.common.Field.BELONG_STATION;
import static com.chuansongmen.common.Field.COLLECT_NUM;
import static com.chuansongmen.common.Field.COLLECT_ROOKIE_NUM;
import static com.chuansongmen.common.Field.NOW_LATITUDE;
import static com.chuansongmen.common.Field.NOW_LONGITUDE;
import static com.chuansongmen.common.Field.REG_ID;
import static com.chuansongmen.common.Field.SEND_NUM;
import static com.chuansongmen.common.Field.WORKER_CATEGORY;
import static com.chuansongmen.common.Field.WORKER_ID;
import static com.chuansongmen.common.Field.WORKER_NAME;
import static com.chuansongmen.common.Field.WORKER_SEX;
import static com.chuansongmen.common.Field.WORKER_STATUS;

public class WorkerConverter implements Converter<ResponseBody, Worker> {
    @Override
    public Worker convert(ResponseBody value) throws IOException {
        String body = value.string();
        Worker worker = new Worker();
        try {
            JSONObject bodyObj = new JSONObject(body);
//            if (bodyObj.getString("code").equals("200")) {
            if (bodyObj.getString("data") != null) {
                String workerString = bodyObj.getString("data");
                JSONObject workerJson = new JSONObject(workerString);
                worker.setId(workerJson.getString(WORKER_ID))
                        .setBelongStation(new Station(workerJson.getString(BELONG_STATION)))
                        .setCollectNum(workerJson.getInt(COLLECT_NUM))
                        .setName(workerJson.getString(WORKER_NAME))
                        .setNow(new Position(workerJson.getDouble(NOW_LONGITUDE),
                                workerJson.getDouble(NOW_LATITUDE)))
                        .setRegId(workerJson.getString(REG_ID))
                        .setSendNum(workerJson.getInt(SEND_NUM))
                        .setSex(workerJson.getInt(WORKER_SEX))
                        .setWorking(workerJson.getInt(WORKER_STATUS) > 0)
                        .setCategory(Worker.Category.values()[workerJson.getInt(WORKER_CATEGORY)]);
                return worker;
            } else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
