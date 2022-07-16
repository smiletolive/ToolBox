package com.ruilin.caipiao.ui;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lib.commui.nova.ui.XBaseFragment;
import com.lib.commui.nova.ui.adapter.BusinessBaseAdapter;
import com.lib.commui.nova.util.ClipboardUtils;
import com.lib.commui.nova.widget.TitleView;
import com.lib.commui.nova.widget.dialog.base.ResultListener;
import com.ruilin.caipiao.R;
import com.ruilin.caipiao.db.Info;
import com.ruilin.caipiao.db.InfoDatabase;
import com.ruilin.caipiao.db.Password;

import java.util.List;

import butterknife.BindView;

public class PasswordListFragment extends XBaseFragment {

    public static PasswordListFragment getInstance() {
        return new PasswordListFragment();
    }

    public PasswordListFragment() {
    }

    @BindView(R.id.rv_pwd)
    RecyclerView rvPwd;

    private BusinessBaseAdapter<Password, PasswordItemViewHolder> adapter;
    @Override
    public void onDependencyInject() {

    }

    @Override
    public void onObserve() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_password_list;
    }

    private BusinessBaseAdapter<Password, PasswordItemViewHolder> getAdapter() {
        return new BusinessBaseAdapter<Password, PasswordItemViewHolder>() {
            @Override
            public PasswordItemViewHolder onCreateContentViewHolder() {
                return new PasswordItemViewHolder(this, new ResultListener<Password>() {
                    @Override
                    public void onCallback(int position, Password data) {
                        showToast("密码已复制");
                        ClipboardUtils.copyText(data.getPassword());
                    }
                });
            }
        };
    }


    @Override
    public void onInitView() {
        super.onInitView();

        initRecycleView();
        initData();
    }

    private void initRecycleView() {
        // 设置布局管理器，第三个参数为是否逆向布局
        rvPwd.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        // 可以提高效率
        rvPwd.setHasFixedSize(true);
        adapter = getAdapter();
        rvPwd.setAdapter(adapter);
    }

    private void initData() {
        adapter.clearData();
        List<Password> infoList = InfoDatabase.getInstance().pwdDao().selectAll();
        if (infoList != null && infoList.size() > 0) {
            adapter.setListData(infoList);
        }
    }
}
