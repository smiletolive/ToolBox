package com.lib.commui.nova.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lib.commui.R;
import com.lib.commui.nova.util.ScreenUtils;
import com.lib.commui.nova.util.XLog;

import androidx.annotation.ColorInt;
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;

public class TitleView extends FrameLayout {

    private static final String TAG = "TitleView";

    private Drawable titleViewBg;

    /**
     *  是否显示标题，默认不显示
     */
    private boolean titleShow;
    private String titleText;
    private int titleTextColor;
    private float titleTextSize;

    private String leftText;
    private int leftTextColor;
    private float leftTextSize;
    private Drawable leftViewIcon;

    private String rightText;
    private int rightTextColor;
    private float rightTextSize;
    private Drawable rightViewIcon;
    private boolean rightShow;

    private String toParamString() {
        return "TitleView{" +
                "titleText='" + titleText + '\'' +
                ", titleTextColor=" + titleTextColor +
                ", titleTextSize=" + titleTextSize +
                ", leftText='" + leftText + '\'' +
                ", leftTextColor=" + leftTextColor +
                ", leftTextSize=" + leftTextSize +
                ", leftViewIcon=" + leftViewIcon +
                ", rightText='" + rightText + '\'' +
                ", rightTextColor=" + rightTextColor +
                ", rightTextSize=" + rightTextSize +
                ", rightViewIcon=" + rightViewIcon +
                "} ";
    }

    public TitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View rootView = LayoutInflater.from(context).inflate(R.layout.base_layout_title, this);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TitleView, 0, 0);
        try {
            titleViewBg = ta.getDrawable(R.styleable.TitleView_titleViewBg);

            titleShow = ta.getBoolean(R.styleable.TitleView_titleShow, false);
            titleText = ta.getString(R.styleable.TitleView_titleText);
            titleTextColor = ta.getColor(R.styleable.TitleView_titleTextColor, getResources().getColor(R.color.basic_lib_color_title));
            //getDimension返回的值为px单位
            titleTextSize = ScreenUtils.px2sp(this.getContext(), ta.getDimension(R.styleable.TitleView_titleTextSize, 0));

            leftText = ta.getString(R.styleable.TitleView_leftText);
            leftTextColor = ta.getColor(R.styleable.TitleView_leftTextColor, getResources().getColor(R.color.basic_lib_color_title));
            leftTextSize = ScreenUtils.px2sp(this.getContext(), ta.getDimension(R.styleable.TitleView_leftTextSize, 0));
            leftViewIcon = ta.getDrawable(R.styleable.TitleView_leftViewIcon);

            rightText = ta.getString(R.styleable.TitleView_titleViewRightText);
            rightTextColor = ta.getColor(R.styleable.TitleView_titleViewRightTextColor, getResources().getColor(R.color.basic_lib_color_title));
            rightTextSize = ScreenUtils.px2sp(this.getContext(), ta.getDimension(R.styleable.TitleView_titleViewRightTextSize, 0));
            rightViewIcon = ta.getDrawable(R.styleable.TitleView_titleViewRightViewIcon);
            rightShow = ta.getBoolean(R.styleable.TitleView_titleViewRightViewShow, false);

//            XLog.d(TAG,toParamString());

            initView(rootView);
        } finally {
            ta.recycle();
        }
    }

    private TextView tvTitle;
    private TextView tvSubTitle;
    private TextView tvRight;

    private void initView(View rootView) {
        if (titleViewBg != null){
            rootView.setBackground(titleViewBg);
        }

        //判断是否显示标题
        if (!TextUtils.isEmpty(titleText) || titleShow){
            tvTitle = findViewById(rootView, R.id.basic_lib_title_tv_title);

            tvTitle.setText(titleText);
            tvTitle.setTextSize(titleTextSize);
            tvTitle.setTextColor(titleTextColor);
        }

        //判断是否显示左边视图
        if (!TextUtils.isEmpty(leftText) || leftViewIcon != null){
            TextView tvLeft = findViewById(rootView, R.id.basic_lib_title_tv_left);

            if (!TextUtils.isEmpty(leftText)){
                tvLeft.setText(leftText);
                tvLeft.setTextSize(leftTextSize);
                tvLeft.setTextColor(leftTextColor);
            }

            //显示图标
            if (leftViewIcon != null){
                //Drawable必须已经setBounds()，进行初始位置、宽和高等信息；
                leftViewIcon.setBounds(0,0,leftViewIcon.getMinimumWidth(),leftViewIcon.getMinimumHeight());
                tvLeft.setCompoundDrawables(leftViewIcon, null, null, null);
            }
        }

        //判断是否显示右边视图
        if (!TextUtils.isEmpty(rightText) || rightViewIcon != null || rightShow){
            tvRight = findViewById(rootView, R.id.basic_lib_title_tv_right);

            if (!TextUtils.isEmpty(rightText)){
                tvRight.setText(rightText);
                tvRight.setTextSize(rightTextSize);
                tvRight.setTextColor(rightTextColor);
            }

            //显示图标
            if (rightViewIcon != null){
                //Drawable必须已经setBounds()，进行初始位置、宽和高等信息；
                rightViewIcon.setBounds(0,0,rightViewIcon.getMinimumWidth(),rightViewIcon.getMinimumHeight());
                tvRight.setCompoundDrawables(rightViewIcon, null, null, null);
            }
        }
    }

    /**
     * 设置标题
     * @param title 标题
     */
    public void setTitleText(String title){
        if (tvTitle == null){
            XLog.w(TAG,"setTitleText view == null");
            return;
        }
        tvTitle.setText(title);
    }

    /**
     * 设置子标题
     * @param title 子标题
     */
    public void setSubTitleText(String title){
        if (tvSubTitle == null){
            XLog.w(TAG,"setSubTitleText view == null");
            return;
        }
        tvSubTitle.setText(title);
    }

    public void setRightTextListener(OnClickListener listener){
        if (tvRight == null){
            XLog.w(TAG,"setRightTextListener tvRight == null");
            return;
        }
        tvRight.setOnClickListener(listener);
    }

    private <T extends View> T findViewById(View rootView, @IdRes int id) {
        return rootView.findViewById(id);
    }

    public void setRightText(String text) {
        tvRight.setText(text);
    }

    public void setRightTextColor(@ColorInt int color) {
        tvRight.setTextColor(color);
    }
}
