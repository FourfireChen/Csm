package com.chuansongmen.base;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Toast;

import com.chuansongmen.util.PermissionUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.EasyPermissions;

import static com.chuansongmen.common.Field.REQUEST_LOCATION;
import static com.chuansongmen.common.Field.REQUEST_LOCATION_AND_NETWORK;

/**
 * 这个类是本项目中封装的基本类，提供一些自己封装的方法，并且提供默认的viewModel
 *
 * @param <T> 这个泛型需要指定的是继承该类的Activity对应的viewModel，这里会自动通过反射获取viewmodel对象
 */

public abstract class BaseActivity<T extends BaseViewModel> extends AppCompatActivity {
    protected T viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //禁止横屏了
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(getContentLayoutId());
        ButterKnife.bind(this);
        // 初始化从其它Activity传过来的数据
        initData();
        // 初始化ViewModel
        initViewModel();
        // 初始化视图
        initView();
        // 初始化绑定关系
        initBind();
        // 初始化服务
        initService();
        PermissionUtil.permissionsCheckAndRequest(this, REQUEST_LOCATION_AND_NETWORK,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.INTERNET);
    }

    protected void initService() {
    }

    protected abstract @LayoutRes
    int getContentLayoutId();

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
        switch (requestCode) {
            case REQUEST_LOCATION:
                PermissionUtil.permissionsCheckAndRequest(this, REQUEST_LOCATION_AND_NETWORK,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET);
                break;
        }
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

    protected void initBind() {
    }

    protected void initView() {
    }

    protected void initData() {
    }

    public void startActivity(Class<?> target, @Nullable Bundle bundle) {
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

    public void toast(final String content) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseActivity.this, content, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
