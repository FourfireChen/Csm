package com.chuansongmen;

import android.app.Application;
import androidx.annotation.NonNull;

import com.chuansongmen.base.BaseViewModel;
import com.chuansongmen.data.IDataRepository;

public class MainViewModel extends BaseViewModel {
    private IDataRepository dataRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        dataRepository = super.dataRepo;
    }

    public void switchWorkerStatus(boolean isWorked){
        //dataRepository.updateWorkerStatus();
    }
}
