package com.chuansongmen.base;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.annotation.NonNull;

import com.chuansongmen.data.DataRepository;
import com.chuansongmen.data.IDataRepository;

/**
 * 由于需要context,所以继承了androidviewmodel而不是viewmodel
 */
public abstract class BaseViewModel extends AndroidViewModel{
    protected IDataRepository dataRepo;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        dataRepo = DataRepository.getInstance();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        //todo:停止repo中正在进行的工作
    }
}
