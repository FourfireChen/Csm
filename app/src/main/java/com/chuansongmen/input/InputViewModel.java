package com.chuansongmen.input;

import android.app.Application;

import com.chuansongmen.base.BaseViewModel;
import com.chuansongmen.common.Callback;
import com.chuansongmen.data.bean.Worker;
import com.chuansongmen.exception.NotInitException;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

class InputViewModel extends BaseViewModel {

    private MutableLiveData<String> inputMessage = new MutableLiveData<>();
    private MutableLiveData<String> pageIdLegality = new MutableLiveData<>();

    InputViewModel(@NonNull Application application) {
        super(application);
    }

    LiveData<String> getInputMessage() {
        return inputMessage;
    }

    LiveData<String> getPageIdLegality() {
        return pageIdLegality;
    }

    /**
     * 检查扫描到的单号是否符合格式
     *
     * @param code 输入的合法性
     * @return 如果不符合，返回"非法单号"空字符串，如果符合，照常返回单号
     */
    void checkPageIdLegality(String code) {
        // TODO: 2018/12/9 检查合法性，如果合法，Post”可使用“
        pageIdLegality.postValue("可使用");
    }

    /**
     * 订单录入
     *
     * @param serialId 订单流水号
     * @param pagerId  订单号
     */
    void input(String serialId, String pagerId) {
        try {
            dataRepo.receiveOrderFromUser(serialId,
                    pagerId,
                    Worker.getInstance().getId(),
                    new Callback<String>() {
                        @Override
                        public void onResponse(String result) {
                            inputMessage.postValue(result);
                        }
                    });
        } catch (NotInitException e) {
            e.printStackTrace();
        }
    }


}
