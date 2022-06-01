package com.lib.commui.nova.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.RecyclerView;

/**
 * ViewHolder基类
 * Created by 2020-11-24
 */
public class BaseViewHolder extends RecyclerView.ViewHolder{

    public Context context;
    public View itemView;

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        context = itemView.getContext();
        this.itemView = itemView;
    }

    // 根据layout id查找对应的控件
    protected <T extends View> T findViewById(int id) {
        return (T) itemView.findViewById(id);
    }

    public Drawable getDrawable(int id){
        return context.getResources().getDrawable(id);
    }

    public int getColor(@ColorRes int id){
        return context.getResources().getColor(id);
    }

    public String getString(@StringRes int resId){
        return context.getString(resId);
    }

    public String getString(@StringRes int resId, Object... formatArgs){
        return context.getString(resId, formatArgs);
    }

}
