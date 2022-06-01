package com.lib.commui.nova.widget.dialog;

import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.lib.commui.R;
import com.lib.commui.nova.widget.dialog.base.BaseAdapter;
import com.lib.commui.nova.widget.dialog.base.BaseDialogFragment;
import com.lib.commui.nova.widget.dialog.base.ResultListener;
import com.lib.commui.nova.widget.dialog.base.StringArrayAdapter;

import java.util.List;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 列表对话框
 */
public class ListDialog<T extends BaseAdapter> extends BaseDialogFragment {

    private String title;
    private String bottomButtonText;
    private List<String> listData;
    private ResultListener<String> mItemClickListener;
    private View.OnClickListener mBottomClickListener;

    ListDialog(String title
            , String bottomButtonText
            , List<String> listData
            , ResultListener<String> itemClickListener
            , View.OnClickListener bottomClickListener) {
        this.title = title;
        this.bottomButtonText = bottomButtonText;
        this.listData = listData;
        this.mItemClickListener = itemClickListener;
        this.mBottomClickListener = bottomClickListener;
    }

    private T mAdapter;

    ListDialog(String title, T adapter) {
        this.title = title;
        this.mAdapter = adapter;
    }

    @Override
    public int getLayoutId() {
        return R.layout.basic_lib_dialog_list;
    }

    @Override
    public int getDialogWidth() {
        //宽度占满整个屏幕
        return WindowManager.LayoutParams.MATCH_PARENT;
    }

    @Override
    public void initView(View view) {
        TextView tvTitle = view.findViewById(R.id.tv_title);
        if (TextUtils.isEmpty(title)){
            tvTitle.setVisibility(View.GONE);
        }else {
            tvTitle.setText(title);
            tvTitle.setVisibility(View.VISIBLE);
        }

        View lineBottomSplit = view.findViewById(R.id.line_bottom_split);
        TextView tvBottom = view.findViewById(R.id.tv_bottom);
        if (TextUtils.isEmpty(bottomButtonText)){
            lineBottomSplit.setVisibility(View.GONE);
            tvBottom.setVisibility(View.GONE);
        }else {
            lineBottomSplit.setVisibility(View.VISIBLE);
            tvBottom.setVisibility(View.VISIBLE);
            tvBottom.setText(bottomButtonText);
            tvBottom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mBottomClickListener != null){
                        mBottomClickListener.onClick(v);
                    }
                }
            });
        }

        initListView(view);
    }

    private void initListView(View view) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);


        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);

        if (mAdapter == null){
            StringArrayAdapter adapter = new StringArrayAdapter(listData, new ResultListener<String>() {
                @Override
                public void onCallback(int position, String data) {
                    ListDialog.this.dismiss();
                    if (mItemClickListener != null){
                        mItemClickListener.onCallback(position, data);
                    }
                }
            });
            recyclerView.setAdapter(adapter);
        }else {
            //使用外部的适配器
            recyclerView.setAdapter(mAdapter);
        }


        //添加Android自带的分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
    }


    public static class Builder<T extends BaseAdapter> {

        private String title;
        private String bottomButtonText;
        private List<String> listData;
        private ResultListener<String> mItemClickListener;
        private View.OnClickListener mBottomClickListener;

        private T adapter;

        public Builder setAdapter(T adapter) {
            this.adapter = adapter;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setBottomButton(String text) {
            this.bottomButtonText = text;
            return this;
        }

        public Builder setListData(List<String> listData) {
            this.listData = listData;
            return this;
        }

        public Builder setItemClickListener(ResultListener<String> listener) {
            this.mItemClickListener = listener;
            return this;
        }

        public Builder setBottomButtonClickListener(View.OnClickListener listener) {
            this.mBottomClickListener = listener;
            return this;
        }

        public ListDialog create(){
            if (adapter == null){
                return new ListDialog(title, bottomButtonText, listData, mItemClickListener, mBottomClickListener);
            }else {
                return new ListDialog(title, adapter);
            }
        }

    }


}
