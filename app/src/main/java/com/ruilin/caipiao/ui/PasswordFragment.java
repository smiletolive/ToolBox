package com.ruilin.caipiao.ui;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lib.commui.nova.ui.XBaseFragment;
import com.lib.commui.nova.widget.TitleView;
import com.lib.commui.nova.widget.dialog.RadioDialogFragment;
import com.lib.commui.nova.widget.dialog.base.ResultListener;
import com.ruilin.caipiao.R;
import com.ruilin.caipiao.db.InfoDatabase;
import com.ruilin.caipiao.db.Password;

import butterknife.BindView;
import butterknife.OnClick;

public class PasswordFragment extends XBaseFragment {

    @BindView(R.id.title_view)
    TitleView titleView;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_folder)
    EditText etFolder;
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_bank_number)
    EditText etBankNumber;
    @BindView(R.id.et_bank_date)
    EditText etBankDate;
    @BindView(R.id.tv_save)
    TextView tvSave;

    private Password mPassword;
    @Override
    public void onDependencyInject() {

    }

    @Override
    public void onObserve() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_password;
    }

    @Override
    public void onInitData() {
        super.onInitData();

        long id = getActivity().getIntent().getLongExtra("id",0l);
        if (id > 0) {
            mPassword = InfoDatabase.getInstance().pwdDao().selectById(id);
        } else {
            mPassword = new Password();
        }
    }

    @OnClick({R.id.tv_save, R.id.tv_type})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_type:
                String[] types = getResources().getStringArray(R.array.pwd_type);
                RadioDialogFragment dialogFragment = new RadioDialogFragment.Builder()
                        .setItemArray(types)
                        .setTitle("类型")
                        .setItemValueArray(new String[]{"0","1","2"})
                        .setSelectedListener(new ResultListener<String>() {
                            @Override
                            public void onCallback(int position, String data) {
                                tvType.setText(types[position]);
                                mPassword.setType(Integer.parseInt(data));
                            }
                        })
                        .create();
                dialogFragment.show(getChildFragmentManager(), "selectType");
                break;
            case R.id.tv_save:
                String name = etName.getText().toString();
                String account = etAccount.getText().toString();
                String password = etPassword.getText().toString();
                String folder = etFolder.getText().toString();
                String bankNumber = etBankNumber.getText().toString();
                String bankDate = etBankDate.getText().toString();

                mPassword.setName(name);
                mPassword.setAccount(account);
                mPassword.setPassword(password);
                mPassword.setFolder(folder);
                mPassword.setBankNumber(bankNumber);
                mPassword.setBankDate(bankDate);
                InfoDatabase.getInstance().savePassword(mPassword);
                break;
        }
    }
}
