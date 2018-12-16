package com.chuansongmen.util;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

public class CallUtil {

    public static void call(Context context, String phoneNumber) {
        Uri phoneUri = Uri.parse("tel:" + phoneNumber);
        Intent intent = new Intent(Intent.ACTION_CALL, phoneUri);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "没有拨打电话权限，请检查权限", Toast.LENGTH_SHORT).show();
            Log.e("Shawn", "call: 没有权限");
            return;
        }
        context.startActivity(intent);
    }
}
