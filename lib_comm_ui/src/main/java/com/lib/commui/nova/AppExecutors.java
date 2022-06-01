package com.lib.commui.nova;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class AppExecutors {

    private static class Holder{
        private static AppExecutors ourInstance = new AppExecutors();
    }

    /**
     * 获取实例,使用静态内部类初始化(解决了按需加载、线程安全的问题，同时实现简洁)
     * @return
     */
    public static AppExecutors getInstance(){
        AppExecutors instance = AppExecutors.Holder.ourInstance;
        return instance;
    }

    private final Executor diskIO;
    private final Executor networkIO;
    private final Executor mainThread;

    private AppExecutors() {
        this.diskIO = Executors.newSingleThreadExecutor();
        this.networkIO = Executors.newFixedThreadPool(3);
        this.mainThread = new MainThreadExecutor();
    }

    public Executor diskIO(){
        return diskIO;
    }

    public Executor networkIO(){
        return networkIO;
    }

    public Executor mainThread(){
        return mainThread;
    }

    private static class MainThreadExecutor implements Executor {

        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable command) {
            mainThreadHandler.post(command);
        }
    }

}
