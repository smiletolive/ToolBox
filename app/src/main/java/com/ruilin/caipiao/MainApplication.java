package com.ruilin.caipiao;

import android.app.Application;

import com.ruilin.caipiao.db.InfoDatabase;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        InfoDatabase.init(this);
    }
}
