package com.chuansongmen.base;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseFragment<T extends BaseViewModel> extends Fragment {
    protected T viewModel;

    protected void startActivity(Class<? extends Activity> activity, @Nullable Bundle bundle) {
        Intent intent = new Intent(getContext(), activity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void startFragmentInActivity(@IdRes int container,
                                           Fragment target,
                                           boolean isAddBack) {
        //todo:封装一个跳转Fragment的方法
        FragmentTransaction transaction = getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(container, target);
        if (isAddBack){
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //这里用了反射，获取了子类泛型
        Type superClass = getClass().getGenericSuperclass();
        if (superClass instanceof ParameterizedType) {
            Type[] types =
                    ((ParameterizedType) superClass).getActualTypeArguments();
            if (types.length > 0)
                viewModel = ViewModelProviders.of(this).get((Class<T>) types[0]);
        }
    }
}
