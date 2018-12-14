package com.chuansongmen.receipt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chuansongmen.R;
import com.chuansongmen.base.BaseFragment;
import com.chuansongmen.util.Util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class InputFragment extends BaseFragment {
    @BindView(R.id.receipt_title)
    TextView receiptTitle;
    @BindView(R.id.receipt_money)
    EditText receiptMoney;
    @BindView(R.id.receipt_confirm)
    Button receiptConfirm;
    @BindView(R.id.receipt_money_title)
    TextView receiptMoneyTitle;
    @BindView(R.id.receipt_money_suggest)
    TextView receiptMoneySuggest;
    Unbinder unbinder;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.receipt_input_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        initTextTypeface();
        return view;
    }

    private void initTextTypeface() {
        Util.setTypeface("fonts/type.ttf",
                getContext().getAssets(),
                receiptTitle,
                receiptConfirm,
                receiptMoneyTitle,
                receiptMoneySuggest);
    }

    @OnClick(R.id.receipt_confirm)
    public void onViewClicked() {
        startFragmentInActivity(R.id.receipt_container, new PayFragment(), true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
