package com.chuansongmen.util;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadUtil {
    private static ThreadPoolExecutor threadPoolExecutor =
            new ThreadPoolExecutor(5, 8, 2, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());

    public static void execute(Runnable runnable) {
        threadPoolExecutor.execute(runnable);
    }
}
