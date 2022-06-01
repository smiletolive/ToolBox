package com.lib.commui.nova.ui;


import com.lib.commui.nova.util.XLog;

import androidx.lifecycle.ViewModel;

/**
 * 抽象ViewModel层
 * ViewModel是界面控制器（Activity/Fragment）的辅助类
 * 1、负责为界面准备数据
 * 2、fragment之间共享数据
 * 3、加载数据
 * @param <ParamType> 页面参数类型实体
 */
public abstract class XBaseViewModel<ParamType> extends ViewModel {

    public final String TAG;

    public XBaseViewModel(){
        TAG = getClass().getSimpleName();
        XLog.d(TAG,"onCreate " + this);
    }

    public ParamType mArgData;

    /**
     * 初始化数据
     * @param data 数据
     */
    public void initData(ParamType data){
        XLog.d(TAG,"initData " + data);
        mArgData = data;
    }

    /**
     * 获取数据
     * @return 数据
     */
    public ParamType getArgData(){
        XLog.d(TAG,"getData " + mArgData);
        return mArgData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        XLog.d(TAG,"onCleared "  + this);
    }
}
