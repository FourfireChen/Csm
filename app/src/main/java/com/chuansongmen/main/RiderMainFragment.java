package com.chuansongmen.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.chuansongmen.R;
import com.chuansongmen.base.BaseFragment;
import com.chuansongmen.career_info.CareerInfoActivity;
import com.chuansongmen.common.MainGridAdapter;
import com.chuansongmen.common.MainItem;
import com.chuansongmen.receipt.ReceiptActivity;
import com.chuansongmen.sendget.SendGetActivity;
import com.chuansongmen.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RiderMainFragment extends BaseFragment<MainViewModel> implements MainGridAdapter.ItemOnClickListener<MainItem> {
    @BindView(R.id.worker_page)
    GridView workerPage;
    Unbinder unbinder;
    @BindView(R.id.worker_next_backtime)
    TextView workerNextBacktime;
    @BindView(R.id.textbottom)
    TextView textbottom;
    private MainGridAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        initPage();
        return view;
    }

    private void initPage() {
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
        MainItem charge = MainItem.RECEIPT;
        charge.setIcon(getResources().getDrawable(R.drawable.ic_attach_money_black_24dp, null));
        charge.setValue(null);
        mainItems.add(2, star);
        mainItems.add(5, charge);
        adapter = new MainGridAdapter(workerPage, mainItems);
        workerPage.setAdapter(adapter);
        adapter.setListener(this);

        Util.setTypeface("fonts/type.ttf",
                getContext().getAssets(),
                workerNextBacktime,
                textbottom);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onClick(MainItem item) {
        switch (item) {
            case TO_SEND: {
                Bundle choice = new Bundle();
                choice.putString(getString(R.string.TYPE), getString(R.string.SEND));
                startActivity(SendGetActivity.class, choice);
                break;
            }

            case TO_GET: {
                Bundle choice = new Bundle();
                choice.putString(getString(R.string.TYPE), getString(R.string.GET));
                startActivity(SendGetActivity.class, choice);
                break;
            }
            case TO_UNLOAD: {
                Bundle choice = new Bundle();
                choice.putString(getString(R.string.TYPE), getString(R.string.UNLOAD));
                startActivity(SendGetActivity.class, choice);
                break;
            }
            case RECEIPT: {
                startActivity(ReceiptActivity.class, null);
                break;
            }
            case STAR: {
                startActivity(CareerInfoActivity.class, null);
                break;
            }
        }
    }
}
