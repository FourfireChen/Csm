package com.chuansongmen.worker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.chuansongmen.R;
import com.chuansongmen.base.BaseFragment;
import com.chuansongmen.base.MainItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class WorkerFragment extends BaseFragment implements WorkerGridAdapter.WorkerPageItemsClickListener {
    @BindView(R.id.worker_page)
    GridView workerPage;
    Unbinder unbinder;
    private WorkerGridAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.worker_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        initPage();
        return view;
    }

    private void initPage(){
        MainItem[] mainItemsArray = new MainItem[]{
                MainItem.SEND_TODAY,
                MainItem.GET_TODAY,
                MainItem.KEY_GOOD,
                MainItem.DETAINED_GOOD,
                MainItem.TO_GET,
                MainItem.TO_SEND,
                MainItem.TO_UNLOAD
        };
        List<MainItem> mainItems = new ArrayList<>(Arrays.asList(mainItemsArray));
        MainItem star = MainItem.STAR;
        star.setIcon(getResources().getDrawable(R.drawable.ic_star_amber_800_24dp, null));
        MainItem charge = MainItem.CHARGE;
        charge.setIcon(getResources().getDrawable(R.drawable.ic_attach_money_black_24dp, null));
        charge.setValue(null);
        mainItems.add(2, star);
        mainItems.add(5, charge);
        adapter = new WorkerGridAdapter(workerPage, mainItems);
        workerPage.setAdapter(adapter);
        adapter.setListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onClick(MainItem item) {
        switch (item){
            case SEND_TODAY:
                break;
            case GET_TODAY:
                break;
            case KEY_GOOD:
                break;
            case DETAINED_GOOD:
                break;
            case TO_SEND:
                break;
            case TO_GET:
                break;
            case TO_UNLOAD:
                break;
            case NEXT_ARRIVAL_TIME:
                break;
            case NEXT_SITE:
                break;
            case EMERGENCY_CONTACT:
                break;
            case NEXT_DEPATRUE_TIME:
                break;
            case NEXT_HANDOVER:
                break;
            case CHARGE:
                break;
            case STAR:
                break;
        }
    }
}
