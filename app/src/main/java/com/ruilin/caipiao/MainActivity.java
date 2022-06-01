package com.ruilin.caipiao;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lib.commui.nova.ui.adapter.BusinessBaseAdapter;
import com.lib.commui.nova.widget.dialog.base.ResultListener;
import com.ruilin.caipiao.db.Info;
import com.ruilin.caipiao.db.InfoDatabase;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rb_ticai)
    RadioButton rbTicai;
    @BindView(R.id.rb_fuli)
    RadioButton rbFuli;
    @BindView(R.id.rg_cai)
    RadioGroup rgCai;
    @BindView(R.id.tv_jixuan)
    TextView tvJixuan;
    @BindView(R.id.tv_baocun)
    TextView tvBaocun;
    @BindView(R.id.tv_qingchu)
    TextView tvQingchu;
    @BindView(R.id.rv_data)
    RecyclerView rvData;

    private BusinessBaseAdapter<Info, DataInfoViewHolder> adapter;
    private int type = Info.TYPE_TIYU;
    private List<Info> dataList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        PermissionUtils.requestMultiPermissions(this, mPermissionGrant);

        initRecycleView();
        initView();
        initData();
    }

    private void initView() {
        rgCai.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_fuli:
                        type = Info.TYPE_FULI;
                        break;
                    case R.id.rb_ticai:
                        type = Info.TYPE_TIYU;
                        break;
                }
            }
        });
    }

    private void initData() {
        dataList.clear();
        adapter.clearData();
        List<Info> infoList = InfoDatabase.getInstance().infoDao().selectAll();
        if (infoList != null && infoList.size() > 0) {
            dataList.addAll(infoList);
            adapter.setListData(infoList);
        }
    }

    @OnClick({R.id.tv_jixuan, R.id.tv_baocun, R.id.tv_qingchu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_jixuan:
                jixuan();
                break;
            case R.id.tv_baocun:
                baocun();
                break;
            case R.id.tv_qingchu:
                initData();
                break;
        }
    }
    private void initRecycleView() {
        // 设置布局管理器，第三个参数为是否逆向布局
        rvData.setLayoutManager(new LinearLayoutManager(getBaseContext(), RecyclerView.VERTICAL, false));
        // 可以提高效率
        rvData.setHasFixedSize(true);
        adapter = getAdapter();
        rvData.setAdapter(adapter);
    }

    private BusinessBaseAdapter<Info, DataInfoViewHolder> getAdapter() {
        return new BusinessBaseAdapter<Info, DataInfoViewHolder>() {
            @Override
            public DataInfoViewHolder onCreateContentViewHolder() {
                return new DataInfoViewHolder(this, new ResultListener<Info>() {
                    @Override
                    public void onCallback(int position, Info data) {

                    }
                });
            }
        };
    }

    private void jixuan() {
        int maxLeft, maxRight;
        Info info = new Info();
        if (Info.TYPE_TIYU == type) {
            info.setType(Info.TYPE_TIYU);
            maxLeft = 35;
            maxRight = 12;
            List<Integer> left = getRandom(maxLeft, 5);
            info.setOne(left.get(0));
            info.setTwo(left.get(1));
            info.setThee(left.get(2));
            info.setFour(left.get(3));
            info.setFive(left.get(4));

            List<Integer> right = getRandom(maxRight, 2);
            info.setSix(right.get(0));
            info.setSeven(right.get(1));
        } else if (Info.TYPE_FULI == type) {
            info.setType(Info.TYPE_FULI);
            maxLeft = 33;
            maxRight = 16;

            List<Integer> left = getRandom(maxLeft, 6);
            info.setOne(left.get(0));
            info.setTwo(left.get(1));
            info.setThee(left.get(2));
            info.setFour(left.get(3));
            info.setFive(left.get(4));
            info.setSix(left.get(5));

            List<Integer> right = getRandom(maxRight, 1);
            info.setSeven(right.get(0));
        }
        info.setTime(System.currentTimeMillis());
        dataList.add(0, info);
        adapter.addDataToFirst(info);
    }

    private void baocun() {
        for (Info info: dataList) {
            if (info.isNew()) {
                InfoDatabase.getInstance().infoDao().insert(info);
            }
        }

        initData();
    }
    /**
     * 获取一组不同随机数
     * @param maxValue 最大值
     * @param num 随机数个数
     * @return
     */
    private List<Integer> getRandom(int maxValue, int num) {
        Set<Integer> set = new HashSet<>();
        Random r = new Random();
        while (true) {
            int n = r.nextInt(maxValue) + 1;
            if (set.add(n)) {
                num--;
            }
            if (num == 0) {
                break;
            }
        }
        List<Integer> list = new ArrayList<>();
        list.addAll(set);
        Collections.sort(list);
        Log.i("xxx","-->"+ ArrayUtils.toString(list));
        return list;
    }

    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {

        }
    };
}