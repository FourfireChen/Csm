package com.chuansongmen.sendget;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SendGetViewPageAdapter extends FragmentPagerAdapter {
    private List<? extends SendGetFragment> fragments;
    private String[] titles;


    SendGetViewPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles == null ? "" : titles[position];
    }


    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }

    public void setFragments(List<? extends SendGetFragment> fragments) {
        this.fragments = fragments;
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
    }
}
