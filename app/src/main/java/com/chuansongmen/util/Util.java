package com.chuansongmen.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.chuansongmen.R;
import com.chuansongmen.common.ProgressListener;
import com.chuansongmen.common.StartListener;

import java.util.Timer;
import java.util.TimerTask;

public class Util {
    private static View progress = null;

    public static void setTypeface(String path, AssetManager manager, TextView... textViews) {
        for (TextView v :
                textViews) {
            v.setTypeface(Typeface.createFromAsset(manager, path));
        }
    }

    /**
     * @param context
     */
    public static void showProgress(Context context, @Nullable StartListener listener) {
        if (progress != null) {
            Toast.makeText(context, "已经弹出", Toast.LENGTH_SHORT).show();
        } else {
            WindowManager windowManager =
                    (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

            progress = View.inflate(context, R.layout.verifying, null);
            Util.setTypeface("fonts/type.ttf", context.getAssets(),
                    (TextView) progress.findViewById(R.id.verifying_text));
            WindowManager.LayoutParams params = new WindowManager.LayoutParams();
            params.height = params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
            windowManager.addView(progress, params);
            if (listener != null)
                listener.onStart();
        }
    }

    /**
     * @param context
     * @param delayToClose
     * @param progressListener 此处onStart和onFinish都有执行，且都运行在主线程中。
     */
    public static void showProgress(final Context context,
                                    int delayToClose,
                                    @Nullable final ProgressListener progressListener) {
        showProgress(context, progressListener);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (progressListener != null) {
                    if (context instanceof Activity) {
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressListener.onFinish();
                                stopProgress(context);
                            }
                        });
                    }
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, delayToClose);
    }

    public static void stopProgress(Context context) {
        if (progress == null) {
            Toast.makeText(context, "没有弹出进度", Toast.LENGTH_SHORT).show();
        } else {
            WindowManager windowManager =
                    (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

            windowManager.removeView(progress);
            progress = null;
        }
    }
}
