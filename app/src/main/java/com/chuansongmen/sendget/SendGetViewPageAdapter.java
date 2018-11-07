package com.chuansongmen.rider.sendget;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class SendGetViewPageAdapter extends FragmentPagerAdapter {
    private List<? extends Fragment> fragments;
    private String[] titles;


    public SendGetViewPageAdapter(FragmentManager fm,
                                  List<? extends Fragment> fragments,
                                  String[] titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
