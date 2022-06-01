package com.lib.commui.nova.widget.dialog.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lib.commui.nova.ui.adapter.BaseViewHolder;
import com.lib.commui.nova.util.XLog;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 通用的适配器
 * @param <DO> 数据类型
 */
public abstract class BaseAdapter<DO, VH extends BaseViewHolder> extends RecyclerView.Adapter<VH> {

    private List<DO> listData;

    public ResultListener<DO> listener;

    public BaseAdapter(List<DO> listData, ResultListener<DO> listener) {
        this.listData = listData;
        this.listener = listener;
    }

    public BaseAdapter() { }

    public void setListData(List<DO> data) {
        if (data == null || data.size() == 0){
            XLog.w(getClass().getSimpleName(),"setListData data == null || data.size() == 0");
            return;
        }
        this.listData = data;
        notifyDataSetChanged();
    }

    /**
     * 添加元素到第一位
     * @param data
     */
    public void addDataToFirst(DO data){
        if (this.listData == null){
            XLog.e(getClass().getSimpleName(),"addDataToFirst error mListData == null");
            return;
        }
        if (data == null){
            XLog.w(getClass().getSimpleName(),"addDataToFirst data == null");
            return;
        }
        listData.add(0, data);
        notifyDataSetChanged();
    }

    /**
     * 添加列表数据到第一位
     * @param data
     */
    public void addListDataToFirst(List<DO> data){
        if (this.listData == null){
            XLog.e(getClass().getSimpleName(),"addListDataToFirst error mListData == null");
            return;
        }
        if (data == null || data.size() == 0){
            XLog.w(getClass().getSimpleName(),"addListDataToFirst data == null || data.size() == 0");
            return;
        }
        listData.addAll(0, data);
        notifyDataSetChanged();
    }

    public void addListData(List<DO> data){
        if (this.listData == null){
            XLog.e(getClass().getSimpleName(),"addListData error mListData == null");
            return;
        }
        if (data == null || data.size() == 0){
            XLog.w(getClass().getSimpleName(),"addListData data == null || data.size() == 0");
            return;
        }
        listData.addAll(data);
        notifyDataSetChanged();
    }

    public List<DO> getListData() {
        return listData;
    }

    public void setListener(ResultListener<DO> listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View itemView = LayoutInflater.from(parent.getContext()).inflate(getViewLayoutId(viewType), parent, false);
        return onHandleCreateViewHolder(itemView);
    }

    /**
     * 创建ViewHolder
     * @param itemView 元素视图
     * @return 返回VH
     */
    protected abstract VH onHandleCreateViewHolder(View itemView);

    /**
     * 获取视图布局
     * @return 视图布局ID
     */
    protected abstract int getViewLayoutId(int viewType);

    @Override
    public void onBindViewHolder(@NonNull VH holder, final int position) {
        final DO item = listData.get(position);
        if (listener != null && isListenerLayoutClick()){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onCallback(position, item);
                }
            });
        }
        onHandleBindViewHolder(holder, position, item);
    }

    /**
     * 是否监听整个布局的点击
     * @return true 是（默认）；false 不是
     */
    public boolean isListenerLayoutClick() {
        return true;
    }

    /**
     * 子类处理绑定视图
     * @param holder 视图Holder
     * @param position 元素位置
     * @param item 元素
     */
    public abstract void onHandleBindViewHolder(VH holder, int position, DO item);

    @Override
    public int getItemCount() {
        return listData == null ? 0 : listData.size();
    }

}
