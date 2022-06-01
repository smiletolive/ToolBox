package com.lib.commui.nova.util;

import android.util.Log;

/**
 * log管理
 * Created by 2020-11-11
 */
public class XLog {

    public static void v(String tag, String msg){
        Log.v(tag, msg);
    }

    public static void i(String tag, String msg){
        Log.i(tag, msg);
    }

    public static void d(String tag, String msg){
        Log.d(tag, msg);
    }

    public static void w(String tag, String msg){
        Log.w(tag, msg);
    }

    public static void e(String tag, String msg){
        Log.e(tag, msg);
    }

    public static void wtf(String tag, Throwable throwable){
        Log.wtf(tag, throwable);
    }

}
