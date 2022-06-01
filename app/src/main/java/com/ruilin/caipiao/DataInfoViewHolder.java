package com.ruilin.caipiao;

import android.view.View;
import android.widget.TextView;

import com.lib.commui.nova.ui.adapter.BusinessBaseAdapter;
import com.lib.commui.nova.ui.adapter.ContentBaseViewHolder;
import com.lib.commui.nova.widget.dialog.base.ResultListener;
import com.ruilin.caipiao.db.Info;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DataInfoViewHolder extends ContentBaseViewHolder<BusinessBaseAdapter<Info, DataInfoViewHolder>, Info> {

    @BindView(R.id.tv_one)
    TextView tvOne;
    @BindView(R.id.tv_two)
    TextView tvTwo;
    @BindView(R.id.tv_three)
    TextView tvThree;
    @BindView(R.id.tv_four)
    TextView tvFour;
    @BindView(R.id.tv_five)
    TextView tvFive;
    @BindView(R.id.tv_hint1)
    TextView tvHint1;
    @BindView(R.id.tv_six)
    TextView tvSix;
    @BindView(R.id.tv_hint2)
    TextView tvHint2;
    @BindView(R.id.tv_seven)
    TextView tvSeven;

    View itemView;
    private ResultListener<Info> listener;

    public DataInfoViewHolder(BusinessBaseAdapter<Info, DataInfoViewHolder> adapter, ResultListener<Info> listener) {
        super(adapter);
        this.listener = listener;
    }

    @Override
    public void onBindContentView(int position, Info data) {
        if (data.getType() == Info.TYPE_FULI) {
            tvHint2.setVisibility(View.VISIBLE);
            tvHint1.setVisibility(View.GONE);
        } else if (Info.TYPE_TIYU == data.getType()) {
            tvHint2.setVisibility(View.GONE);
            tvHint1.setVisibility(View.VISIBLE);
        }

        if (data.isNew()) {
            tvHint1.setTextColor(itemView.getContext().getResources().getColor(R.color.teal_700, null));
            tvHint2.setTextColor(itemView.getContext().getResources().getColor(R.color.teal_700, null));
        } else {
            tvHint1.setTextColor(itemView.getContext().getResources().getColor(R.color.black, null));
            tvHint2.setTextColor(itemView.getContext().getResources().getColor(R.color.black, null));
        }

        tvOne.setText(int2Str(data.getOne()));
        tvTwo.setText(int2Str(data.getTwo()));
        tvThree.setText(int2Str(data.getThee()));
        tvFour.setText(int2Str(data.getFour()));
        tvFive.setText(int2Str(data.getFive()));
        tvSix.setText(int2Str(data.getSix()));
        tvSeven.setText(int2Str(data.getSeven()));
    }

    @Override
    public void onInflateContentView(View itemView) {
        ButterKnife.bind(this, itemView);
        this.itemView = itemView;
    }

    @Override
    public int onGetContentResId() {
        return R.layout.item_info;
    }

    private String int2Str(int n) {
        StringBuilder sb = new StringBuilder();
        if (n < 10){
            sb.append(0);
        }
        sb.append(n);
        return sb.toString();
    }
}
