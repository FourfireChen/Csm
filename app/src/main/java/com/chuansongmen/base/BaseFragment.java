package com.chuansongmen.base;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;

public abstract class BaseFragment<T extends ViewModel> extends Fragment{
    protected T viewModel;

    public BaseFragment(Class<T> model) {
        viewModel = ViewModelProviders.of(this).get(model);
    }
}
