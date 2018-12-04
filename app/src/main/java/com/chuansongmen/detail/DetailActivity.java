package com.chuansongmen.detail;

import android.os.Bundle;

import com.chuansongmen.R;
import com.chuansongmen.base.BaseActivity;
import com.chuansongmen.data.bean.Order;

import java.util.Objects;
import java.util.function.ToDoubleBiFunction;

import androidx.annotation.Nullable;
import butterknife.ButterKnife;

public class DetailActivity extends BaseActivity<DetailViewModel> {
    private Order order;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        ButterKnife.bind(this);

        order = Objects.requireNonNull(getIntent().getExtras())
                .getParcelable(getString(R.string.ORDER));

        initView();
    }

    protected void initView() {
        // TODO: 2018/12/4 解析订单
    }


}
