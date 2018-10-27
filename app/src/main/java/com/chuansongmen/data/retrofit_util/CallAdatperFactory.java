package com.chuansongmen.data.retrofit_util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

public class CallAdatperFactory extends CallAdapter.Factory {



    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {

        return null;
    }
}
