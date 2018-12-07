package com.chuansongmen.detail;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuansongmen.R;
import com.chuansongmen.base.BaseActivity;
import com.chuansongmen.data.bean.Order;
import com.chuansongmen.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;

public class DetailActivity extends BaseActivity<DetailViewModel> {

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
    @BindView(R.id.detail_finish_time)
    TextView detailFinishTime;
    @BindView(R.id.detail_remark)
    TextView detailRemark;
    @BindView(R.id.detail_first_icon)
    ImageView detailFirstIcon;
    @BindView(R.id.detail_first_text)
    TextView detailFirstText;
    @BindView(R.id.detail_first)
    CardView detailFirst;
    @BindView(R.id.detail_second_icon)
    ImageView detailSecondIcon;
    @BindView(R.id.detail_second_text)
    TextView detailSecondText;
    @BindView(R.id.detail_second)
    CardView detailSecond;
    @BindView(R.id.detail_third_icon)
    ImageView detailThirdIcon;
    @BindView(R.id.detail_third_text)
    TextView detailThirdText;
    @BindView(R.id.detail_third)
    CardView detailThird;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.textView)
    TextView textView;
    private Order order;
    private IDetailAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        ButterKnife.bind(this);
        Bundle data = getIntent().getExtras();

        if (data != null) {
            order = data.getParcelable(getString(R.string.ORDER));
        }

        if (order != null)
            adapter = new DetailAdapter(order, viewModel);

        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        Util.setTypeface(getString(R.string.FONT),
                getAssets(),
                textView,
                textView2,
                detailOrderStatus);

        detailFromName.setText(adapter.getFrom().getName());
        detailFromPhone.setText(adapter.getFrom().getPhone());
        detailFromAddress.setText(adapter.getFrom().getAddress());

        detailToName.setText(adapter.getTo().getName());
        detailToPhone.setText(adapter.getTo().getPhone());
        detailToAddress.setText(adapter.getTo().getAddress());

        detailSerialnumber.setText(adapter.getSerialId());

        orderId.setText(adapter.getOrderId());

        detailRemark.setText(adapter.getRemark());

        detailOrderStatus.setText(adapter.getStatus());

        List<IDetailAdapter.ButtonStatus> statuses = adapter.getButtonStatus(this);

        detailFirst.setVisibility(GONE);
        detailSecond.setVisibility(GONE);
        detailThird.setVisibility(GONE);

        List<TextView> detailButtonTexts =
                new ArrayList<>(Arrays.asList(detailFirstText, detailSecondText, detailThirdText));
        List<ImageView> detailButtonIcons =
                new ArrayList<>(Arrays.asList(detailFirstIcon, detailSecondIcon, detailThirdIcon));
        List<CardView> detailButtons =
                new ArrayList<>(Arrays.asList(detailFirst, detailSecond, detailThird));

        for (int i = 0; i < statuses.size(); i++) {
            final IDetailAdapter.ButtonStatus status = statuses.get(i);
            TextView text = detailButtonTexts.get(i);
            ImageView icon = detailButtonIcons.get(i);
            CardView button = detailButtons.get(i);
            if (status != null) {
                button.setVisibility(View.VISIBLE);
                text.setText(status.getText());
                icon.setImageDrawable(status.getIcon());
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (status.getText()) {
                                // 滞留件点击处理
                            case "取消滞留":

                                break;
                                // 下面这两个是重点件的处理
                            case "收件":

                                break;
                            case "派出":

                                break;
                                // 正在收件、正在派件两种处理，要再分开一下
                            case "滞留":

                                break;
                                // 正在收件处理，扫描录入
                            case "录入":

                                break;
                                // 已收件补差
                            case "补差":

                                break;
                                // 已收件印单
                            case "印单":

                                break;
                                // 正在派件移交
                            case "移交":

                                break;
                        }
                    }
                });
            }
        }
    }
}
