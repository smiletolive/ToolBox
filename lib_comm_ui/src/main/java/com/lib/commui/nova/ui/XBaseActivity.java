package com.lib.commui.nova.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MotionEvent;


import com.lib.commui.R;
import com.lib.commui.nova.util.BarUtils;
import com.lib.commui.nova.util.KeyboardUtils;
import com.lib.commui.nova.util.XLog;

import java.util.List;

import androidx.annotation.ColorInt;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

/**
 * Activity基类
 */
public abstract class XBaseActivity extends AppCompatActivity {

    public final String TAG = "MF_" + getClass().getSimpleName();

    /**
     * 启动一个activity，并关闭自己
     * @param cls 目标类
     */
    public void startActivityForPopSelf(Class<?> cls){
        startActivity(new Intent(this, cls));
        this.finish();
    }

    /**
     * 动态替换Fragment
     * @param containerViewId Identifier of the container whose fragment(s) are
     * to be replaced.
     * @param fragment The new fragment to place in the container.
     */
    public void replaceFragment(@IdRes int containerViewId, @NonNull Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(containerViewId, fragment).commit();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //点击屏幕空白区域隐藏软键盘
        KeyboardUtils.hideActivityInput(XBaseActivity.this, getCurrentFocus(), ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        XLog.d(TAG,"onCreate");
    }

    /**
     * 修改状态栏颜色，支持5.0以上版本
     * @param activity The activity.
     * @param color The status bar's color.
     */
    public static void setStatusBarColor(@NonNull final Activity activity,
                                         @ColorInt final int color) {
        BarUtils.setStatusBarColor(activity, color);
    }

    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        XLog.d(TAG,"onRestoreInstanceState");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        XLog.d(TAG,"onSaveInstanceState");
    }

    @Override
    public void onStart() {
        super.onStart();
        XLog.d(TAG,"onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        XLog.d(TAG,"onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        XLog.d(TAG,"onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        XLog.d(TAG,"onStop");
    }

    @Override
    public void onRestart() {
        super.onRestart();
        XLog.d(TAG,"onRestart");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        XLog.d(TAG,"onDestroy");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //将onActivityResult传递给Fragment
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}