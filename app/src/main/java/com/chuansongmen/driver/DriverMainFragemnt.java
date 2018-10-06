package com.chuansongmen.driver;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.chuansongmen.R;
import com.chuansongmen.base.BaseFragment;
import com.chuansongmen.common.MainGridAdapter;
import com.chuansongmen.data.MainItem;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DriverMainFragemnt extends BaseFragment<MainViewModel> implements MainGridAdapter.ItemOnClickListener<MainItem> {
    @BindView(R.id.driver_main_gridview)
    GridView driverMainGridview;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.driver_main_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        initGrid();
        return view;
    }

    private void initGrid() {
        MainItem[] items = new MainItem[]{
                MainItem.NEXT_ARRIVAL_TIME,
                MainItem.NEXT_SITE,
                MainItem.EMERGENCY_CONTACT,
                MainItem.RUNNING,
                MainItem.NEXT_DEPATRUE_TIME,
                MainItem.NEXT_HANDOVER
        };
        List<MainItem> mainItems = Arrays.asList(items);
        mainItems.get(mainItems.indexOf(MainItem.RUNNING)).setValue("请勿关闭程序或后台");
        mainItems.get(mainItems.indexOf(MainItem.EMERGENCY_CONTACT))
                .setIcon(getResources().getDrawable(R.drawable.ic_phone_black_24dp, null));
        MainGridAdapter adapter = new MainGridAdapter(driverMainGridview, mainItems);
        adapter.setListener(this);
        driverMainGridview.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(MainItem item) {

    }
}
