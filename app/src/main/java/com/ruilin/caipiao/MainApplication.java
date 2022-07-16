package com.ruilin.caipiao;

import android.app.Application;

import com.lib.commui.nova.util.Utils;
import com.ruilin.caipiao.db.InfoDatabase;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        InfoDatabase.init(this);
    }
}
