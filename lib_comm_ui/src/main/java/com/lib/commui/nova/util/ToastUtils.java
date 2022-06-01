package com.lib.commui.nova.util;

import android.os.Build;
import android.widget.Toast;

import androidx.annotation.StringRes;

import com.lib.commui.R;

/**
 * Created by 2020-12-21
 */
public class ToastUtils {

    public static void showToast(final String msg){
        Utils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(Utils.getApp(), msg, Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }

    public static void showToast(@StringRes final int resId) {
        Utils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(Utils.getApp(), resId, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    public static void showToastLong(@StringRes final int resId) {
        Utils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(Utils.getApp(), resId, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    public static void showToastByImagePath() {
        Utils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int ris = R.string.image_storage_per;
                if (Build.VERSION.SDK_INT >= 30) {
                    ris = R.string.image_storage_per_30;
                }
                Toast toast = Toast.makeText(Utils.getApp(), ris, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }
}
