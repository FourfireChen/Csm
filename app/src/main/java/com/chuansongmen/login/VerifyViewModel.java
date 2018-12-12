package com.chuansongmen.login;

import android.app.Application;

import com.chuansongmen.base.BaseViewModel;
import com.chuansongmen.common.Callback;

import java.util.Random;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import static com.chuansongmen.data.DataRepository.FAIL;

public class VerifyViewModel extends BaseViewModel {

    /**
     * @return 成功时返回验证码，失败是返回{@Link DataRepository.FAIL}
     */
    private MutableLiveData<String> sendMessageResult = new MutableLiveData<>();

    public VerifyViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<String> getSendMessageResult() {
        return sendMessageResult;
    }

    void sendVerifyCode(String phonenumber) {
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 4; i++) {
            stringBuffer.append(random.nextInt(10));
        }
        final String code = stringBuffer.toString();
        // TODO: 2018/12/12 这里做错了，要有手机号码
        dataRepo.sendMessage(code, new Callback<Boolean>() {
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
}
