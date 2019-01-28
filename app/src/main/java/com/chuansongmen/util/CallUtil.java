package com.chuansongmen.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import static android.Manifest.permission.CALL_PHONE;

public class CallUtil {
    private static final String TAG = "CallUtil";

    public static void call(Context context, String phoneNumber) {
        Uri phoneUri = Uri.parse("tel:" + phoneNumber);
        Intent intent = new Intent(Intent.ACTION_CALL, phoneUri);
        if (ActivityCompat.checkSelfPermission(context, CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "没有拨打电话权限，请检查权限", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "拨打电话: 没有权限");
        } else {
            context.startActivity(intent);
        }
    }
}
