package com.ruilin.caipiao.ui;

import android.view.View;
import android.widget.TextView;

import com.lib.commui.nova.ui.adapter.BusinessBaseAdapter;
import com.lib.commui.nova.ui.adapter.ContentBaseViewHolder;
import com.lib.commui.nova.widget.dialog.base.ResultListener;
import com.ruilin.caipiao.R;
import com.ruilin.caipiao.db.Password;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PasswordItemViewHolder extends ContentBaseViewHolder<BusinessBaseAdapter<Password, PasswordItemViewHolder>, Password> {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_pwd)
    TextView tvPwd;
    View itemView;

    private ResultListener<Password> listener;

    public PasswordItemViewHolder(BusinessBaseAdapter<Password, PasswordItemViewHolder> adapter, ResultListener<Password> listener) {
        super(adapter);
        this.listener = listener;
    }

    @Override
    public void onBindContentView(int position, Password data) {

    }

    @Override
    public void onInflateContentView(View itemView) {
        this.itemView = itemView;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public int onGetContentResId() {
        return R.layout.item_password;
    }
}
