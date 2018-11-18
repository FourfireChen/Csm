package com.chuansongmen.base;

import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 这个类是本项目中封装的基本类，提供一些自己封装的方法，并且提供默认的viewModel
 * @param <T> 这个泛型需要指定的是继承该类的Activity对应的viewModel，这里会自动通过反射获取viewmodel对象
 */

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

    protected void initView() {}

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
