package com.chuansongmen.login;

import android.app.Application;

import com.chuansongmen.base.BaseViewModel;
import com.chuansongmen.common.Callback;
import com.chuansongmen.data.bean.Worker;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import static com.chuansongmen.data.DataRepository.FAIL;

public class LoginViewModel extends BaseViewModel {

    /**
     * @return 成功时返回验证码，失败是返回{@Link DataRepository.FAIL}
     */
    private MutableLiveData<String> sendMessageResult = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoginSuccess = new MutableLiveData<>();
    private Map.Entry<String, String> verify = null;

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<String> getSendMessageResult() {
        return sendMessageResult;
    }

    public MutableLiveData<Boolean> getIsLoginSuccess() {
        return isLoginSuccess;
    }


    /**
     * 发送验证码
     *
     * @param phoneNumber 手机号码
     */
    void sendVerifyCode(String phoneNumber) {
        generateVerifyCode(phoneNumber);
        dataRepo.sendMessage(phoneNumber, verify.getValue(), new Callback<Boolean>() {
            @Override
            public void onResponse(Boolean isSuccess) {
                sendMessageResult.postValue(isSuccess ? verify.getValue() : FAIL);
            }
        });
    }

    /**
     * 生成随机验证码
     * 并做缓存，存在verify这个Entry里面
     *
     * @param phoneNumber 手机号码
     */
    private void generateVerifyCode(String phoneNumber) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        verify = new AbstractMap.SimpleEntry<>(phoneNumber, stringBuilder.toString());
    }


    /**
     * 检查手机号码和验证码是否对应
     *
     * @param phoneNumber 手机号码
     * @param code        验证码
     * @return 是否对应
     */
    private boolean checkVerifyCode(String phoneNumber, String code) {
        return verify != null &&
                phoneNumber.equals(verify.getKey()) &&
                code.equals(verify.getValue());
    }

    public void cacheUserPhoneNumber(String phoneNumber) {
        dataRepo.cacheUserPhoneNumber(getApplication(), phoneNumber);
    }


    public LiveData<String> hadUserPhoneNumberCache() {
        final MutableLiveData<String> hadCache = new MutableLiveData<>();
        dataRepo.getCacheUserPhoneNumber(getApplication(), new Callback<String>() {
            @Override
            public void onResponse(String result) {
                hadCache.postValue(result);
            }
        });
        return hadCache;
    }

    /**
     * 登录
     *
     * @param phone 手机号码
     */
    void loginByCache(String phone) {
        dataRepo.getWorkerInfo(phone, new Callback<Worker>() {
            @Override
            public void onResponse(Worker result) {
                isLoginSuccess.postValue(result != null);
            }
        });
    }

    void loginByVerify(String phone, String code) {
        isLoginSuccess.postValue(checkVerifyCode(phone, code));
    }

    void clearVerifyEntry() {
        verify = null;
    }

    boolean checkPhoneFormate(String phone) {
        return !phone.isEmpty() &&
                phone.toCharArray().length == 11;
    }

    boolean checkVerifyFormate(String code) {
        return !code.isEmpty() && code.toCharArray().length == 4;
    }
}
