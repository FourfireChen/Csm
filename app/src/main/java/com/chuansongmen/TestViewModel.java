package com.chuansongmen;

import android.app.Application;

import com.chuansongmen.base.BaseViewModel;
import com.chuansongmen.common.Callback;

import androidx.annotation.NonNull;

public class TestViewModel extends BaseViewModel {

    public TestViewModel(@NonNull Application application) {
        super(application);
    }

//    public void addTestWorker() {
//        dataRepo.addTestWorker(new Callback<Boolean>() {
//            @Override
//            public void onResponse(Boolean result) {
//
//            }
//        });
//    }


}
