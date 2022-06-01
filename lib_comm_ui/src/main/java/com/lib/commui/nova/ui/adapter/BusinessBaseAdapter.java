package com.lib.commui.nova.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lib.commui.nova.util.XLog;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 业务适配器基类
 * @param <DO> 数据对象
 */
public abstract class BusinessBaseAdapter<DO, CVH extends ContentBaseViewHolder> extends RecyclerView.Adapter<BusinessBaseViewHolder<CVH>> {

    private List<DO> mListData = new ArrayList<>();

    /**
     * 无效值
     */
    public static final int INVALID_VALUE = -1;

    /**
     * 选中元素的索引
     */
    public int mSelectedPosition = INVALID_VALUE;

    public BusinessBaseAdapter() { }

    @NonNull
    @Override
    public BusinessBaseViewHolder<CVH> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //创建内容视图ViewHolder
        CVH contentViewHolder = onCreateContentViewHolder();
        //内容布局ID
        int layoutId = contentViewHolder.onGetContentResId();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        //创建业务ViewHolder
        return new BusinessBaseViewHolder<CVH>(itemView, contentViewHolder);
    }

    /**
     * 创建内容的ViewHolder （子类实现）
     * @return 内容的ViewHolder
     */
    abstract public CVH onCreateContentViewHolder();

    @Override
    public void onBindViewHolder(@NonNull BusinessBaseViewHolder holder, int position) {
        //绑定视图内容
        holder.mContentBaseViewHolder.onBindContentView(position, mListData.get(position));
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    public void setListData(List<DO> data) {
        XLog.d(getClass().getSimpleName(),"setListData data = " + data);
        if (data == null || data.size() == 0){
            XLog.w(getClass().getSimpleName(),"setListData data == null || data.size() == 0");
            return;
        }
        this.mListData.clear();
        this.mListData.addAll(data);
        notifyDataSetChanged();
    }

    /**
     * 添加元素到第一位
     * @param data
     */
    public void addDataToFirst(DO data){
        if (this.mListData == null){
            XLog.e(getClass().getSimpleName(),"addDataToFirst error mListData == null");
            return;
        }
        if (data == null){
            XLog.w(getClass().getSimpleName(),"addDataToFirst data == null");
            return;
        }
        mListData.add(0, data);
        notifyDataSetChanged();
    }

    /**
     * 添加列表数据到第一位
     * @param data
     */
    public void addListDataToFirst(List<DO> data){
        if (this.mListData == null){
            XLog.e(getClass().getSimpleName(),"addListDataToFirst error mListData == null");
            return;
        }
        if (data == null || data.size() == 0){
            XLog.w(getClass().getSimpleName(),"addListDataToFirst data == null || data.size() == 0");
            return;
        }
        mListData.addAll(0, data);
        notifyDataSetChanged();
    }

    public void addListData(List<DO> data){
        if (this.mListData == null){
            XLog.e(getClass().getSimpleName(),"addListData error mListData == null");
            return;
        }
        if (data == null || data.size() == 0){
            XLog.w(getClass().getSimpleName(),"addListData data == null || data.size() == 0");
            return;
        }
        mListData.addAll(data);
        notifyDataSetChanged();
    }

    public List<DO> getListData() {
        return mListData;
    }

    public void clearData(){
        if (mListData != null){
            mListData.clear();
            mSelectedPosition = INVALID_VALUE;
            notifyDataSetChanged();
        }
    }

    public void deleteListItem(int position) {
        if (mListData != null
                && (position >= 0)
                && (position < mListData.size())){
            mListData.remove(position);
            notifyItemChanged(position);
        }
    }

    public void deleteListItem(DO item) {
        if (mListData != null
                && item != null){
            mListData.remove(item);
            notifyDataSetChanged();
        }
    }

    public void updateListItem(DO item) {
        if (mListData != null
                && (item != null)){
            mListData.add(item);
            notifyDataSetChanged();
        }
    }

    /**
     * 获取元素所在位置
     * @param item 元素
     * @return 获取元素所在位置
     */
    public int getItemPosition(DO item){
        if (mListData != null
                && (item != null)){
            return mListData.indexOf(item);
        }
        return -1;
    }
}
