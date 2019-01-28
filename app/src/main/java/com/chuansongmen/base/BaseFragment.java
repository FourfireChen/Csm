package com.chuansongmen.base;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.chuansongmen.util.PermissionUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import pub.devrel.easypermissions.EasyPermissions;

import static com.chuansongmen.common.Field.REQUEST_LOCATION;

public abstract class BaseFragment<T extends BaseViewModel> extends Fragment {
    protected T viewModel;

    protected void startActivity(Class<? extends AppCompatActivity> activity,
                                 @Nullable Bundle bundle) {
        Intent intent = new Intent(getContext(), activity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
        switch (requestCode) {
            case REQUEST_LOCATION:
                PermissionUtil.permissionsCheckAndRequest(this,
                        REQUEST_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION);
                break;
        }
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

    protected void toast(String content) {
        Toast.makeText(getActivity(), content, Toast.LENGTH_SHORT).show();
    }
}
