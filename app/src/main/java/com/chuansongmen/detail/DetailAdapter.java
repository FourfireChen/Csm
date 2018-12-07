package com.chuansongmen.detail;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.chuansongmen.R;
import com.chuansongmen.data.bean.Order;

import java.util.ArrayList;
import java.util.List;

public class DetailAdapter implements IDetailAdapter {
    private Order order;
    private DetailViewModel viewModel;
    DetailAdapter(Order order, DetailViewModel viewModel) {
        this.order = order;
        this.viewModel = viewModel;
    }

    @Override
    public IDetailAdapter.Customer getFrom() {
        String name = "";
        String address = "";
        String phone = order.getUserId();
        return new IDetailAdapter.Customer(name, phone == null ? "" : phone, address);
    }

    @Override
    public IDetailAdapter.Customer getTo() {
        String name = order.getRecipientName();
        String address = order.getRecipientAddress();
        String phone = order.getRecipientPhone();
        return new IDetailAdapter.Customer(name != null ? name : "",
                phone != null ? phone : "",
                address != null ? address : "");
    }

    @Override
    public String getStatus() {
        String status = "未知状态";
        if (order.getStatus() == Order.Status.HAS_SENDED) {
            status = "已完成";
        } else if (order.getStatus() == Order.Status.HAS_PICKED_UP ||
                order.getStatus() == Order.Status.IN_STATION ||
                order.getStatus() == Order.Status.TRANSPOTING) {
            status = "已收件";
        } else if (order.isDelay()) {
            status = "滞留件";
        } else if (order.isImportant()) {
            status = "重点件";
        } else if (order.getStatus() == Order.Status.NON_PICK_UP) {
            status = "等待收件";
        } else if (order.getStatus() == Order.Status.SENDING) {
            status = "正在派件";
        }
        return status;
    }

    @Override
    public String getRemark() {
        return order.getRemark() == null ? "无" : order.getRemark();
    }

    /**
     * 订单号
     *
     * @return
     */
    @Override
    public String getOrderId() {
        String orderId = order.getPagerId();
        return orderId != null ? orderId : "";
    }

    @Override
    public String getSerialId() {
        String serialId = order.getId();
        return serialId != null ? serialId : "";
    }

    @Override
    public List<IDetailAdapter.ButtonStatus> getButtonStatus(final Context context) {
        // TODO: 2018/12/6 所有的点击事件还没做
        List<IDetailAdapter.ButtonStatus> buttonStatuses = new ArrayList<>();
        if (order.getStatus() == Order.Status.NON_PICK_UP ||
                order.getStatus() == Order.Status.SENDING) {
            if (order.isDelay()) {
                IDetailAdapter.ButtonStatus status = new IDetailAdapter.ButtonStatus();
                status.setIcon(context.getDrawable(R.drawable.logo_icon));
                status.setText("取消滞留");
                buttonStatuses.add(status);
            } else if (order.isImportant()) {
                ButtonStatus status = new ButtonStatus();
                String text = "";
                switch (order.getStatus()) {
                    case NON_PICK_UP:
                        text = "收件";
                        break;
                    case SENDING:
                        text = "派出";
                        break;
                }
                status.setText(text);
                status.setIcon(context.getDrawable(R.drawable.logo_icon));
                buttonStatuses.add(status);
            }
            return buttonStatuses;
        }

        List<Drawable> icons = new ArrayList<>();
        List<String> texts = new ArrayList<>();
        List<View.OnClickListener> listeners = new ArrayList<>();
        switch (order.getStatus()) {
            case NON_PICK_UP:
                icons.add(context.getDrawable(R.drawable.ic_restore_black_48dp));
                texts.add("滞留");
                icons.add(context.getDrawable(R.drawable.ic_camera_amber_800_36dp));
                texts.add("录入");
                break;
            case HAS_PICKED_UP:
            case IN_STATION:
            case TRANSPOTING:
                icons.add(context.getDrawable(R.drawable.ic_restore_black_48dp));
                texts.add("滞留");
                icons.add(context.getDrawable(R.drawable.ic_attach_money_24dp));
                texts.add("补差");
                icons.add(context.getDrawable(R.drawable.ic_print_black_24dp));
                texts.add("印单");
                break;
            case SENDING:
                icons.add(context.getDrawable(R.drawable.ic_restore_black_48dp));
                texts.add("滞留");
                icons.add(context.getDrawable(R.drawable.ic_compare_arrows_24dp));
                texts.add("移交");
                break;
            case HAS_SENDED:
                break;
        }
        for (int i = 0; i < icons.size(); i++) {
            ButtonStatus status =
                    new ButtonStatus();
            status.setIcon(icons.get(i));
            status.setText(texts.get(i));
            buttonStatuses.add(status);
        }
        return buttonStatuses;
    }


}
