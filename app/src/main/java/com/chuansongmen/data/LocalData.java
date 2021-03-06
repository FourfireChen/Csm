package com.chuansongmen.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.chuansongmen.R;

class LocalData {
    String getCacheUserPhoneNumber(Context context) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(context.getString(R.string.sharedpreferences_name),
                        Context.MODE_PRIVATE);
        return sharedPreferences.getString(context.getString(R.string.phoneNumber), "");
    }

    void cacheUserPhoneNumber(Context context, String phoneNumber) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(context.getString(R.string.sharedpreferences_name),
                        Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.phoneNumber), phoneNumber);
        editor.apply();
    }

    void clearUserCache(Context context, String id) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(context.getString(R.string.sharedpreferences_name),
                        Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
