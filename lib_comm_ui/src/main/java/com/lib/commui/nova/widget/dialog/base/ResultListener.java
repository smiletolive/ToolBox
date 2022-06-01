package com.lib.commui.nova.widget.dialog.base;

/**
 * Created by 2020-11-10
 */
public interface ResultListener<T> {

    /**
     * 回调
     * @param position 元素点击位置
     * @param data data 处理后的数据
     */
    void onCallback(int position, T data);
}
