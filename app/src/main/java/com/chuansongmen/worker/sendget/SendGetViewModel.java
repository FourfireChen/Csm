package com.chuansongmen.worker.sendget;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import com.chuansongmen.base.BaseViewModel;
import com.chuansongmen.data.bean.Task;
import java.util.ArrayList;

public class SendGetViewModel extends BaseViewModel {
    private MutableLiveData<ArrayList<Task>> tasks = new MutableLiveData<>();

    public SendGetViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<ArrayList<Task>> getTasks() {
        //todo:获取数据更新，通知更新
        return tasks;
    }
}
