package com.lib.commui.nova.ui.adapter;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 内容ViewHolder 基类
 * Created by 2021-1-21
 */
public abstract class ContentBaseViewHolder<AD extends RecyclerView.Adapter, DO>{

    final private AD mAdapter;

    public ContentBaseViewHolder(AD adapter){
        mAdapter = adapter;
    }

    public AD getAdapter() {
        return mAdapter;
    }


    /**
     * 内容视图绑定数据 (子类需要重写)
     * @param position 数据位置
     * @param data 数据
     */
    abstract public void onBindContentView(int position, DO data);

    /**
     * 创建内容视图  (子类需要重写)
     */
    abstract public void onInflateContentView(View itemView);

    /**
     * 获取内容布局  (子类需要重写)
     * @return layout ID 布局ID
     */
    abstract public int onGetContentResId();

}
