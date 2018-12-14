package com.chuansongmen.login;

import android.app.Application;

import com.chuansongmen.base.BaseViewModel;
import com.chuansongmen.common.Callback;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import static com.chuansongmen.data.DataRepository.FAIL;

public class VerifyViewModel extends BaseViewModel {

    /**
     * @return 成功时返回验证码，失败是返回{@Link DataRepository.FAIL}
     */
    private MutableLiveData<String> sendMessageResult = new MutableLiveData<>();
    private Map.Entry<String, String> verify = null;

    public VerifyViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<String> getSendMessageResult() {
        return sendMessageResult;
    }

    void sendVerifyCode(String phoneNumber) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        final String code = stringBuilder.toString();
        verify = new AbstractMap.SimpleEntry<>(phoneNumber, code);
        dataRepo.sendMessage(phoneNumber, code, new Callback<Boolean>() {
            @Override
            public void onResponse(Boolean isSuccess) {
                if (isSuccess) {
                    sendMessageResult.postValue(code);
                } else {
                    sendMessageResult.postValue(FAIL);
                }
            }
        });
    }

    boolean checkVerifyCode(String phoneNumber, String code)
    {
        return verify != null &&
                phoneNumber.equals(verify.getKey()) &&
                code.equals(verify.getValue());
    }
}
