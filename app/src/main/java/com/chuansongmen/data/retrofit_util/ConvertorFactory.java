package com.chuansongmen.data.retrofit_util;

import com.chuansongmen.data.bean.Order;
import com.chuansongmen.data.bean.Position;
import com.chuansongmen.data.bean.Route;
import com.chuansongmen.data.bean.Worker;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class ConvertorFactory extends Converter.Factory {
    private OrdersConvertor ordersConvertor = new OrdersConvertor();
    private PositionConvertor positionConvertor = new PositionConvertor();
    private RoutesConvertor routesConvertor = new RoutesConvertor();
    private WorkerConvertor workerConvertor = new WorkerConvertor();

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type,
                                                            Annotation[] annotations,
                                                            Retrofit retrofit) {
        if (type instanceof ParameterizedType) {
            Type[] actualTypeArgs = ((ParameterizedType) type).getActualTypeArguments();
            if (actualTypeArgs.length > 0) {
                Type actualType = actualTypeArgs[0];
                if (actualType == Order.class)
                    return ordersConvertor;
                else if (actualType == Route.class)
                    return routesConvertor;
            }
        } else if (type == Position.class) {
            return positionConvertor;
        } else if (type == Worker.class) {
            return workerConvertor;
        }
        return super.responseBodyConverter(type, annotations, retrofit);
    }
}
