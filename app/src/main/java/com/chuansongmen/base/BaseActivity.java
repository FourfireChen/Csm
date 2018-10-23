package com.chuansongmen.base;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseActivity<T extends BaseViewModel> extends AppCompatActivity {
    protected T viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //禁止横屏了
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initViewModel();
    }

    /**
     * 这里用了反射，获取了子类的泛型Class
     */
    private void initViewModel() {
        Type superClass = getClass().getGenericSuperclass();
        if (superClass instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) superClass).getActualTypeArguments();
            if (types.length > 0)
                viewModel = ViewModelProviders.of(this).get((Class<T>) types[0]);
        }
    }

    protected void startActivity(Class<?> target, @Nullable Bundle bundle) {
        Intent intent = new Intent(this, target);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void startActivity(String action, @Nullable Bundle bundle) {
        Intent intent = new Intent(action);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivity(intent);
    }

}
