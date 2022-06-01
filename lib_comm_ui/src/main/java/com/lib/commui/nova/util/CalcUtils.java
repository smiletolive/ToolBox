package com.lib.commui.nova.util;

import android.text.TextUtils;

import java.util.Locale;

public class CalcUtils {

    public static String calcStorage(double in) {
        try {
            if (in >= 1024) {
                double out = in / 1024f;
                return String.format("%.1fG", out);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return String.format("%.1fM", in);
    }

    public static String calcNumber(String number) {
        if (TextUtils.isEmpty(number)) return "";
        try {
            int num = Integer.parseInt(number);
            int n = num / 1000;
            if (n > 0) {
                int m = n / 10;
                if (m > 0) {
                    return String.format(Locale.ROOT, "%d万", m);
                } else {
                    return String.format(Locale.ROOT, "%d千", n);
                }
            } else
                return String.valueOf(num);
        }catch (NumberFormatException e){
        }
        return "";
    }
}
