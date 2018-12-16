package com.chuansongmen.detail;

import android.app.Application;
import android.os.Bundle;

import com.chuansongmen.R;
import com.chuansongmen.base.BaseActivity;
import com.chuansongmen.base.BaseViewModel;
import com.chuansongmen.common.Callback;
import com.chuansongmen.confirm.ConfirmActivity;
import com.chuansongmen.data.bean.Order;
import com.chuansongmen.input.InputActivity;
import com.chuansongmen.print.PrintActivity;
import com.chuansongmen.receipt.ReceiptActivity;
import com.chuansongmen.transfer.TransferActivity;

import androidx.annotation.NonNull;

import static com.chuansongmen.data.DataRepository.SUCCESS;

class DetailViewModel extends BaseViewModel {

    DetailViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 处理每个不同情况下的点击事件
     */
    void handleClickEvent(BaseActivity<DetailViewModel> activity, String text, Order order) {
        Bundle data = new Bundle();
        data.putParcelable(activity.getString(R.string.order), order);
        switch (text) {
            // 滞留件点击处理
            case "取消滞留":
                cancelDelay(activity, order);
                break;
            // 下面这两个是重点件的处理
            // 正在收件、正在派件两种处理，要再分开一下
            case "派出":

                break;
            case "滞留":
                activity.startActivity(ConfirmActivity.class, data);
                break;
            // 正在收件处理，扫描录入
            case "录入":
                activity.startActivity(InputActivity.class, data);
                break;
            // 已收件补差
            case "补差":
                activity.startActivity(ReceiptActivity.class, null);
                break;
            // 已收件印单
            case "印单":
                // TODO: 2018/12/8 这里的数据应该是有的
                activity.startActivity(PrintActivity.class, data);
                break;
            // 正在派件移交
            case "移交":
                activity.startActivity(TransferActivity.class, data);
                break;
        }
    }

    private void cancelDelay(final BaseActivity activity, Order order) {
        dataRepo.changeDelay(order.getPagerId(), 1, new Callback<String>() {
            @Override
            public void onResponse(String result) {
                if (result.equals(SUCCESS)) {
                    activity.toast("取消滞留成功");
                    activity.finish();
                } else {
                    activity.toast("取消滞留失败：" + result);
                }
            }
        });
    }
}
