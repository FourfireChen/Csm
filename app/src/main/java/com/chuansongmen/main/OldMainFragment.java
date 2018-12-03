package com.chuansongmen.main;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.chuansongmen.R;
import com.chuansongmen.base.BaseFragment;
import com.chuansongmen.career_info.CareerInfoActivity;
import com.chuansongmen.common.MainItem;
import com.chuansongmen.data.bean.Order;
import com.chuansongmen.receipt.ReceiptActivity;
import com.chuansongmen.scan.ScanActivity;
import com.chuansongmen.sendget.SendGetActivity;
import com.chuansongmen.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OldMainFragment extends BaseFragment<MainViewModel> implements CompoundButton.OnCheckedChangeListener,
        MainGridAdapter.ItemOnClickListener<MainItem> {
    @BindView(R.id.main_bottom_left)
    TextView mainBottomLeft;
    @BindView(R.id.main_bottom_right)
    TextView mainBottomRight;
    @BindView(R.id.main_drawer_workswitch)
    Switch mainDrawerWorkSwitch;
    @BindView(R.id.main_drawer_layout)
    DrawerLayout mainDrawerLayout;
    @BindView(R.id.worker_page)
    GridView workerPage;
    @BindView(R.id.textbottom)
    TextView textbottom;
    @BindView(R.id.worker_next_backtime)
    TextView workerNextBacktime;
    @BindView(R.id.main_bottom_center)
    ImageView mainBottomCenter;
    @BindView(R.id.main_title)
    TextView mainTitle;
    private MainGridAdapter adapter;
    private List<MainItem> mainItems;
    private List<List<Order>> orders = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment_old, container, false);
        ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }


    private void initView() {

        mainDrawerWorkSwitch.setOnCheckedChangeListener(this);
        MainItem[] mainItemsArray = new MainItem[]{
                MainItem.SEND_TODAY,
                MainItem.GET_TODAY,
                MainItem.KEY_GOOD,
                MainItem.DETAINED_GOOD,
                MainItem.TO_GET,
                MainItem.TO_SEND,
                MainItem.TO_UNLOAD
        };
        mainItems = new ArrayList<>(Arrays.asList(mainItemsArray));
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

        Util.setTypeface(getString(R.string.FONT),
                getActivity().getAssets(),
                textbottom,
                workerNextBacktime,
                mainBottomLeft,
                mainBottomRight,
                mainTitle);
    }

    private void initData() {
        viewModel.getOrders().observe(this, new Observer<List<List<Order>>>() {
            @Override
            public void onChanged(List<List<Order>> lists) {
                orders = lists;
                for (MainItem item :
                        mainItems) {
                    int number = -1;
                    // TODO: 2018/11/18 这个地方做得不好，这些数字这么写不优雅
                    // TODO: 2018/11/18 以及，这个地方原型不太好。回站待卸和今日已收要分辨，这里暂时先展示
                    switch (item) {
                        case SEND_TODAY:
                            number = lists.get(5).size();
                            break;
                        case GET_TODAY:
                            number = lists.get(1).size();
                            break;
                        case KEY_GOOD:
                            number = lists.get(2).size() + lists.get(6).size();
                            break;
                        case DETAINED_GOOD:
                            number = lists.get(3).size() + lists.get(7).size();
                            break;
                        case TO_SEND:
                            number = lists.get(4).size();
                            break;
                        case TO_GET:
                            number = lists.get(0).size();
                            break;
                        case TO_UNLOAD:
                            number = lists.get(1).size();
                            break;
                    }
                    if (number != -1) {
                        item.setValue(String.valueOf(number));
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
        viewModel.updateOrders();
    }

    @Override
    public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
        /**
         * 改变职工是否上班状态
         */
        // TODO: 2018/11/17 这个地方要考虑健壮性，如果按钮点击失败怎么办
        viewModel.getWorkerStatus().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (!aBoolean) {
                    Toast.makeText(getContext(), "改变状态失败，请关闭并重试", Toast.LENGTH_SHORT).show();
                } else if (isChecked) {
                    Toast.makeText(getContext(), "上班了", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "下班了", Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewModel.updateWorkerStatus(isChecked ? 1 : 0);
    }


    DrawerLayout getMainDrawerLayout() {
        return mainDrawerLayout;
    }

    @Override
    public void onClick(MainItem item) {
        Class<? extends AppCompatActivity> target = null;
        Bundle data = null;
        String[] title = null;
        int startIndex = 0;
        int endIndex = 0;
        switch (item) {
            case TO_SEND:
                data = new Bundle();
                startIndex = 4;
                endIndex = 8;
                title = new String[]{"待派件", "已派件", "重点件", "滞留派件"};
                target = SendGetActivity.class;
                break;
            case TO_GET:
            case TO_UNLOAD:
                data = new Bundle();
                startIndex = 0;
                endIndex = 4;
                title = new String[]{"待收件", "已收件", "重点件", "滞留收件"};
                target = SendGetActivity.class;
                break;
            case RECEIPT:
                target = ReceiptActivity.class;
                break;
            case STAR:
                target = CareerInfoActivity.class;
                break;
        }
        if (data != null) {
            data.putStringArray(getString(R.string.TITLE), title);
            for (int i = startIndex; i < endIndex; i++) {
                data.putParcelableArrayList(String.valueOf(i),
                        (ArrayList<? extends Parcelable>) orders.get(i));
            }
        }
        if (target != null)
            startActivity(target, data);
    }

    @OnClick({R.id.main_bottom_left,
            R.id.main_bottom_center,
            R.id.main_bottom_right,
            R.id.main_toolbar_me})
    public void onViewClicked(View view) {
        Class<? extends AppCompatActivity> target = null;
        Bundle data = null;
        String[] title = null;
        int startIndex = 0;
        int endIndex = 0;
        switch (view.getId()) {
            case R.id.main_bottom_left:
                data = new Bundle();
                startIndex = 0;
                endIndex = 4;
                title = new String[]{"待收件", "已收件", "重点件", "滞留收件"};
                target = SendGetActivity.class;
                break;
            case R.id.main_bottom_center:
                target = ScanActivity.class;
                break;
            case R.id.main_bottom_right:
                data = new Bundle();
                startIndex = 4;
                endIndex = 8;
                title = new String[]{"待派件", "已派件", "重点件", "滞留派件"};
                target = SendGetActivity.class;
                break;
            case R.id.main_toolbar_me:
                mainDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.main_toolbar_info:
                // TODO: 2018/11/18 这里还有没做的
                break;
        }
        if (data != null) {
            data.putStringArray(getString(R.string.TITLE), title);
            for (int i = startIndex; i < endIndex; i++) {
                data.putParcelableArrayList(String.valueOf(i),
                        (ArrayList<? extends Parcelable>) orders.get(i));
            }
        }
        if (target != null)
            startActivity(target, data);
    }
}
