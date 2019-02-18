package com.chuansongmen.util;

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
    private OrdersConverter ordersConverter = new OrdersConverter();
    private PositionConverter positionConverter = new PositionConverter();
    private RoutesConverter routesConverter = new RoutesConverter();
    private WorkerConverter workerConverter = new WorkerConverter();

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type,
                                                            Annotation[] annotations,
                                                            Retrofit retrofit) {
        if (type instanceof ParameterizedType) {
            Type[] actualTypeArgs = ((ParameterizedType) type).getActualTypeArguments();
            if (actualTypeArgs.length > 0) {
                Type actualType = actualTypeArgs[0];
                if (actualType == Order.class)
                    return ordersConverter;
                else if (actualType == Route.class)
                    return routesConverter;
            }
        } else if (type == Position.class) {
            return positionConverter;
        } else if (type == Worker.class) {
            return workerConverter;
        }
        return super.responseBodyConverter(type, annotations, retrofit);
    }
}
