package com.ruilin.caipiao.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lib.commui.nova.ui.XBaseActivity;
import com.lib.commui.nova.ui.XBaseFragment;
import com.lib.commui.nova.util.XLog;
import com.ruilin.caipiao.R;

import java.io.Serializable;

public class CommonActivity extends XBaseActivity {

    private Fragment getPageFragment(String pageName) {
        Fragment fragment = null;
        return fragment;
    }

    /**
     * 页面名称KEY
     */
    private static final String KEY_PAGE_NAME = "KEY_PAGE_NAME";

    /**
     * 页面参数KEY
     */
    private static final String KEY_PAGE_PARAMS = "KEY_PAGE_PARAMS";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_lib_activity_common);

        initStatusBarView();

        loadFragment();

    }

    private void loadFragment() {
        Intent args = getIntent();
        if (args == null) {
            XLog.e(TAG, "loadFragment args == null");
            return;
        }
        String pageName = args.getStringExtra(KEY_PAGE_NAME);
        Fragment fragment = getPageFragment(pageName);
        if (fragment == null) {
            XLog.e(TAG, "loadFragment fragment == null");
            return;
        }
        //向Fragment传递参数
        Serializable serializable = args.getSerializableExtra(KEY_PAGE_PARAMS);
        if (serializable != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(XBaseFragment.PAGE_PARAMS_KEY, serializable);
            fragment.setArguments(bundle);
        }
        replaceFragment(R.id.layout_fragment_container, fragment);
    }

    /**
     * 初始化状态栏
     */
    private void initStatusBarView() {
        View statusBarView = findViewById(R.id.status_bar_view);
        if (statusBarView != null) {
            ViewGroup.LayoutParams layoutParams = statusBarView.getLayoutParams();
            layoutParams.height = getStatusBarHeight();
            setStatusBarTextBlackColor(true);
        }
    }

    /**
     * 利用反射获取状态栏高度
     * 参考：利用布局里添加占位状态栏 https://www.jianshu.com/p/dc20e98b9a90
     *
     * @return 状态栏高度
     */
    private int getStatusBarHeight() {
        int result = 0;
        //获取状态栏高度的资源id
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 设置状态栏字体颜色
     *
     * @param black true 黑色；false 白色
     */
    private void setStatusBarTextBlackColor(boolean black) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int visib = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            if (black) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)//白色标题栏，把字变黑
                    visib = visib | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            getWindow().getDecorView().setSystemUiVisibility(visib);
        }
    }

}
