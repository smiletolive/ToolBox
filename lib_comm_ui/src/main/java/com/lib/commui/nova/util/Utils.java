package com.lib.commui.nova.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;

import java.util.Random;

/**
 * Utils初始化相关
 * Created on 2017/5/11.
 */

public class Utils {

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        mContext = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (mContext != null) {
            return mContext;
        }
        throw new NullPointerException("u should init first");
    }

    public static Resources getResources() {
       Context context = getContext();
       return context.getResources();
    }

    /**
     * 获取随机数
     * @param min 最小值 （包含）
     * @param max 最大值（不包含）
     * @return 随机生成最大与最小之间的值，不包含最大值，包含最小值
     */
    public static int getRandom(int min, int max) {
        Random random = new Random();
        return (random.nextInt(max) % (max - min + 1) + min);
    }

    public static Context getApp() {
        return mContext;
    }

    public interface Consumer<T> {
        void accept(T t);
    }

    private static final Handler UTIL_HANDLER  = new Handler(Looper.getMainLooper());

    public static void runOnUiThread(final Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
        } else {
            UTIL_HANDLER.post(runnable);
        }
    }

    public static void postDelayed(final Runnable runnable, long delayMillis) {
        UTIL_HANDLER.postDelayed(runnable, delayMillis);
    }

    public static void removeCallbacks(final Runnable runnable){
        UTIL_HANDLER.removeCallbacks(runnable);
    }

}
