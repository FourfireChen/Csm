package com.chuansongmen.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

public abstract class BaseFragment<T extends BaseViewModel> extends Fragment {
    protected T viewModel;

    protected void startActivity(Class<? extends AppCompatActivity> activity, @Nullable Bundle bundle) {
        Intent intent = new Intent(getContext(), activity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void startFragmentInActivity(@IdRes int container,
                                           Fragment target,
                                           boolean isAddBack) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(container, target);
        if (isAddBack) {
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
