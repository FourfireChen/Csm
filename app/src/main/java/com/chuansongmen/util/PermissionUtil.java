package com.chuansongmen.util;

import android.app.Activity;

import androidx.fragment.app.Fragment;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

public class PermissionUtil {
    public static boolean permissionsCheckAndRequest(Fragment fragment,
                                                     int requestCode,
                                                     String... perms) {
        if (!EasyPermissions.hasPermissions(fragment.getContext(),
                perms)) {
            EasyPermissions.requestPermissions(new PermissionRequest.Builder(fragment.getActivity(),
                    requestCode,
                    perms)
                    .setRationale("员工客户端必须获取权限")
                    .setNegativeButtonText("取消")
                    .setPositiveButtonText("确定").build());
            return false;
        } else {
            return true;
        }
    }

    public static boolean permissionsCheckAndRequest(Activity activity,
                                                     int requestCode,
                                                     String... perms) {
        if (!EasyPermissions.hasPermissions(activity,
                perms)) {
            EasyPermissions.requestPermissions(new PermissionRequest.Builder(activity,
                    requestCode,
                    perms)
                    .setRationale("员工客户端必须获取权限")
                    .setNegativeButtonText("取消")
                    .setPositiveButtonText("确定").build());
            return false;
        } else {
            return true;
        }
    }
}
