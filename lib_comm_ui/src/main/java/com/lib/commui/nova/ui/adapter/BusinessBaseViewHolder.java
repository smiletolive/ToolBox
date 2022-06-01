package com.lib.commui.nova.ui.adapter;

import android.view.View;

/**
 * 业务ViewHolder 基类
 * 用于：绑定内容视图的ViewHolder，调用具体的内容视图方法
 * @param <CVH>
 */
public class BusinessBaseViewHolder<CVH extends ContentBaseViewHolder> extends BaseViewHolder {

    /**
     * 内容视图ViewHolder
     */
    CVH mContentBaseViewHolder;

    public BusinessBaseViewHolder(View itemView, CVH contentViewHolder) {
        super(itemView);
        mContentBaseViewHolder = contentViewHolder;
        //真正创建内容视图
        contentViewHolder.onInflateContentView(itemView);
    }

}
