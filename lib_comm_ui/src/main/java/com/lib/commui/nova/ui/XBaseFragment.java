package com.lib.commui.nova.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.lib.commui.R;
import com.lib.commui.nova.util.BarUtils;
import com.lib.commui.nova.util.ColorUtils;
import com.lib.commui.nova.util.XLog;
import com.lib.commui.nova.widget.dialog.LoadingDialog;
import com.lib.commui.nova.util.ToastUtils;

import androidx.annotation.ColorRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class XBaseFragment extends Fragment{

    /**
     * 依赖注入
     */
    public abstract void onDependencyInject();

    /**
     * 观察
     */
    public abstract void onObserve();

    /**
     * 子类布局
     * @return 布局id
     */
    public abstract int getLayoutId();

    public void onRequestData() {
        Log.d(TAG,"onRequestData");
    }

    LoadingDialog loadingDialog;

    public void dismissLoadingView(){
        if (loadingDialog != null){
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }

    public void showLoadingView(String msg){
        dismissLoadingView();

        loadingDialog = new LoadingDialog.Builder()
                .setMessage(msg)
                .create();
        loadingDialog.show(this.getChildFragmentManager(), TAG);
    }

    /**
     * 显示吐司提示
     * 可在子线程调用
     * @param msg 需要提示的信息
     */
    public void showToast(final String msg){
        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showToast(msg);
            }
        });

    }

    /**
     * 显示吐司提示
     * 可在子线程调用
     * @param resId 需要提示的信息
     */
    public void showToast(@StringRes final int resId){
        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showToast(resId);
            }
        });
    }

    public static final String PAGE_PARAMS_KEY = "PAGE_PARAMS_KEY";

    /**
     * 获取数据
     * @param <T> 数据类型
     * @return 数据 （可能为null）
     */
    public <T> T getArgumentData(){
        Bundle args = getArguments();
        if (args == null){
            XLog.e(TAG,"getArgumentData args == null");
            return null;
        }
        Object data = args.get(PAGE_PARAMS_KEY);
        XLog.d(TAG,"getArgumentData " + data);
        if (data == null){
            return null;
        }else {
            return (T) data;
        }
    }

    /**
     * 重写此方法可设置fragment背景色
     * 默认是白色
     */
    @ColorRes
    public int onFragmentBackgroundColor() {
        return android.R.color.white;
    }


    public final String TAG = "MF_" + getClass().getSimpleName();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG,"onAttach");
        //官方指导：使用 Fragment 时，应在 Fragment 的 onAttach() 方法中注入 Dagger。在这种情况下，此操作可以在调用 super.onAttach() 之前或之后完成。
        onDependencyInject();
        Log.d(TAG,"onAttach end");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");
        onObserve();
    }

    private Unbinder unbinder;

    protected View rootView;

    /**
     * 获取View
     * @param id view的ID
     * @param <T> 泛型
     * @return 返回View
     */
    public <T extends View> T findViewById(@IdRes int id){
        if (rootView == null){
            return null;
        }
        return rootView.findViewById(id);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView");
        if (rootView == null){
            rootView = inflater.inflate(getLayoutId(), container, false);
        }
        unbinder = ButterKnife.bind(this, rootView);
        if (preInitStatusBarColor()){
            setStatusBarColor();
        }
        onInitData();
        initBaseView();
        onInitView();
        return rootView;
    }

    /**
     * 设置状态栏颜色
     */
    public void setStatusBarColor() {
        BarUtils.setStatusBarColor(getActivity(), ColorUtils.getColor(onGetStatusBarColorId()));
    }

    /**
     * 状态栏颜色ID
     * 子类可自定义状态栏颜色
     * @return 颜色ID
     */
    public int onGetStatusBarColorId() {
        return R.color.basic_lib_color_status_bar;
    }

    /**
     * 预初始化状态栏颜色
     * @return true 执行；false 不执行
     */
    public boolean preInitStatusBarColor(){
        return true;
    }

    /**
     * 初始化数据
     */
    public void onInitData() {

    }

    /**
     * 初始视图
     */
    public void onInitView() {

    }



    /**
     * 父类直接初始化默认视图
     */
    private void initBaseView() {
        View titleTvLeft = findViewById(R.id.basic_lib_title_tv_left);
        if (titleTvLeft != null){
            titleTvLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!onHandleTitleLeftViewClick()){
                        //子类处理标题栏左边视图点击事件，则父类不处理
                        getActivity().finish();
                    }
                }
            });
        }
    }

    /**
     * 子类处理标题栏左边视图点击事件，则父类不处理
     * @return true 子类处理；false 父类默认处理（返回）
     */
    public boolean onHandleTitleLeftViewClick() {
        return false;
    }

    /**
     * 替换Fragment
      * @param containerViewId Identifier of the container whose fragment(s) are
      * to be replaced.
      * @param fragment The new fragment to place in the container.
     */
    public void replaceFragment(@IdRes int containerViewId, @NonNull Fragment fragment) {
        getChildFragmentManager().beginTransaction().replace(containerViewId, fragment).commit();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG,"onViewCreated");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG,"onActivityCreated");
        onRequestData();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG,"onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG,"onResume");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.d(TAG,"onHiddenChanged hidden = " + hidden);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG,"onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG,"onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG,"onDestroyView");
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG,"onDetach");
    }

}
