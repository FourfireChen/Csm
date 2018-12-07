package com.chuansongmen.detail;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuansongmen.R;
import com.chuansongmen.base.BaseActivity;
import com.chuansongmen.data.bean.Order;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static com.chuansongmen.detail.DetailActivityOld.ButtonText.balance;
import static com.chuansongmen.detail.DetailActivityOld.ButtonText.print;
import static com.chuansongmen.detail.DetailActivityOld.ButtonText.transfer;

public class DetailActivityOld extends BaseActivity<DetailViewModel> {
    @BindView(R.id.detail_first)
    CardView detailFirst;
    @BindView(R.id.detail_second)
    CardView detailSecond;

    enum ButtonText {
        delay("滞留"),
        input("录入"),
        balance("补差"),
        print("印单"),
        getCancel("收件消除"),
        getNow("现在收件"),
        transfer("移交"),
        sendCancel("派出消除"),
        sendNow("现在收件");
        private String description;

        ButtonText(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return description;
        }
    }

    @BindView(R.id.detail_from_name)
    TextView detailFromName;
    @BindView(R.id.detail_from_phone)
    TextView detailFromPhone;
    @BindView(R.id.detail_from_address)
    TextView detailFromAddress;
    @BindView(R.id.detail_call_sender)
    ImageView detailCallSender;
    @BindView(R.id.detail_to_name)
    TextView detailToName;
    @BindView(R.id.detail_to_phone)
    TextView detailToPhone;
    @BindView(R.id.detail_to_address)
    TextView detailToAddress;
    @BindView(R.id.detail_call_getter)
    ImageView detailCallGetter;
    @BindView(R.id.detail_serialnumber)
    TextView detailSerialnumber;
    @BindView(R.id.order_id)
    TextView orderId;
    @BindView(R.id.detail_order_status)
    TextView detailOrderStatus;
    @BindView(R.id.detail_remark)
    TextView detailRemark;
    @BindView(R.id.detail_first_icon)
    ImageView detailFirstIcon;
    @BindView(R.id.detail_first_text)
    TextView detailFirstText;
    @BindView(R.id.detail_second_icon)
    ImageView detailSecondIcon;
    @BindView(R.id.detail_second_text)
    TextView detailSecondText;
    @BindView(R.id.detail_third_icon)
    ImageView detailThirdIcon;
    @BindView(R.id.detail_third_text)
    TextView detailThirdText;
    @BindView(R.id.detail_third)
    CardView detailThird;
    private Order order;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        ButterKnife.bind(this);

        Bundle data = getIntent().getExtras();

        if (data != null) {
            order = data.getParcelable(getString(R.string.ORDER));
        }

//        order = Objects.requireNonNull(getIntent().getExtras())
//                .getParcelable(getString(R.string.ORDER));

        initView();
    }

    protected void initView() {
        orderToView(order);
        generateButtonByOrder();
    }

    private void generateButtonByOrder() {
        // TODO: 2018/12/5 生成
        if (order.isImportant()) {
            if (order.getStatus() == Order.Status.NON_PICK_UP) ;

        } else if (order.isDelay()) {

        } else {
            switch (order.getStatus()) {
                case NON_PICK_UP:
                    break;
                case HAS_PICKED_UP:
                    detailSecondIcon.setImageDrawable(getDrawable(R.drawable.ic_attach_money_24dp));
                    detailSecondText.setText(balance.toString());
                    detailThirdIcon.setImageDrawable(getDrawable(R.drawable.ic_print_black_24dp));
                    detailThirdText.setText(print.toString());
                    break;
                case IN_STATION:
                    break;
                case TRANSPOTING:
                    break;
                case SENDING:
                    detailSecondIcon.setImageDrawable(getDrawable(R.drawable.ic_compare_arrows_24dp));
                    detailSecondText.setText(transfer.toString());
                    detailThird.setVisibility(GONE);
                    break;
                case HAS_SENDED:
                    break;
            }
        }

    }

    private void orderToView(Order order) {
        // 寄件人电话
        if (order.getUserId() != null && !order.getUserId().isEmpty())
            detailFromPhone.setText(String.valueOf(order.getUserId()));
        // 寄件人名字、地址，暂时没有
        detailFromName.setVisibility(GONE);
        detailFromAddress.setVisibility(GONE);
        // 收件人姓名
        if (order.getRecipientName() != null && !order.getRecipientName().isEmpty())
            detailToName.setText(order.getRecipientName());
        // 收件人电话
        if (order.getRecipientPhone() != null && !order.getRecipientPhone().isEmpty())
            detailToPhone.setText(order.getRecipientPhone());
        // 收件人地址
        if (order.getRecipientAddress() != null && !order.getRecipientAddress().isEmpty())
            detailToAddress.setText(order.getRecipientAddress());
        // 订单状态
        if (order.isDelay()) {
            detailOrderStatus.setText("滞留状态");
            detailOrderStatus.setTextColor(Color.RED);
        } else if (order.isImportant()) {
            detailOrderStatus.setText("重点件请优先收派");
            detailOrderStatus.setTextColor(Color.RED);
        } else if (order.getStatus() == Order.Status.HAS_SENDED) {
            detailOrderStatus.setText("已完成");
        } else {
            detailOrderStatus.setText(order.getStatus().toString());
        }
        // 备注
        detailRemark.setText(
                order.getRemark() != null && !order.getRemark().isEmpty() ? order.getRemark() :
                        "无");
        // 流水号
        detailSerialnumber.setText(order.getId());
        // 订单号
        orderId.setText(
                order.getPagerId() != null && !order.getPagerId().isEmpty() ? order.getPagerId() :
                        "未录入");
    }

}
