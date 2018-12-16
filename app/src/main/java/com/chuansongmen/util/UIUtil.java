package com.chuansongmen.util;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.widget.TextView;

public class UIUtil {

    public static void setTypeface(String path, AssetManager manager, TextView... textViews) {
        for (TextView v :
                textViews) {
            v.setTypeface(Typeface.createFromAsset(manager, path));
        }
    }
}
