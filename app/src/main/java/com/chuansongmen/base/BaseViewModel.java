package com.chuansongmen.base;

import android.app.Application;

import com.chuansongmen.data.DataRepository;
import com.chuansongmen.data.IDataRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

/**
 * 由于需要context,所以继承了androidviewmodel而不是viewmodel
 */
public abstract class BaseViewModel extends AndroidViewModel {
    protected IDataRepository dataRepo;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        dataRepo = DataRepository.getInstance();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        dataRepo.stopAll();
    }
}
