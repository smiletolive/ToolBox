package com.lib.commui.nova.widget.dialog;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lib.commui.R;
import com.lib.commui.nova.util.XLog;
import com.lib.commui.nova.widget.dialog.base.BaseDialogFragment;
import com.lib.commui.nova.widget.dialog.base.ResultListener;

import java.util.Arrays;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;

/**
 * 单选弹窗
 */
public class RadioDialogFragment extends BaseDialogFragment {

    private String title;

    private String cancelStr;
    @ColorRes
    private int cancelTextColor;
    private int cancelBtnBgRes;

    private boolean isCancelable;


    /**
     * 元素数组（用于显示）
     */
    String[] itemArray;
    /**
     * 元素值数组（用于存储）
     * 元素值数组跟元素数组 一一对应
     */
    String[] itemValueArray;
    /**
     * 默认值
     */
    String defaultValue;

    /**
     * 选中监听器
     */
    ResultListener<String> mSelectedListener;

    /**
     * 确认监听器
     */
    ResultListener<String> confirmListener;

    /**
     * 创建实例
     * @param title 标题
     * @param cancelStr 取消按钮的文字
     * @param cancelTextColor 取消按钮的文字颜色
     * @param cancelBtnBgRes 取消按钮背景颜色
     * @param isCancelable 是否可以点击返回键或者按弹窗外部来关闭弹出框
     * @param itemArray 元素数组（用于显示）
     * @param itemValueArray 元素值数组（用于存储）
     * @param defaultValue 默认值
     * @param selectedListener 选中监听器
     */
    public RadioDialogFragment(String title
            , String cancelStr
            , int cancelTextColor
            , int cancelBtnBgRes
            , boolean isCancelable
            , String[] itemArray, String[] itemValueArray, String defaultValue
            , ResultListener<String> selectedListener
            , ResultListener<String> confirmListener) {
        this.title = title;
        this.cancelStr = cancelStr;
        this.cancelTextColor = cancelTextColor;
        this.cancelBtnBgRes = cancelBtnBgRes;
        this.isCancelable = isCancelable;
        this.itemArray = itemArray;
        this.itemValueArray = itemValueArray;
        this.defaultValue = defaultValue;
        this.mSelectedListener = selectedListener;
        this.confirmListener = confirmListener;

        XLog.d(TAG,"RadioDialogFragment title = " + title + " " + Arrays.toString(itemArray) + " " + Arrays.toString(itemValueArray) + " " + defaultValue);
    }

    @Override
    public int getDialogGravity() {
        return Gravity.CENTER;
    }

    @Override
    public int getLayoutId() {
        return R.layout.basic_lib_dialog_radio;
    }

    @Override
    public int getDialogAnimation() {
        return R.style.basicLibCenterDialogAnimation;
    }

    @Override
    public boolean isDialogCancelable() {
        return isCancelable;
    }

    @Override
    public void initView(View view) {
        if (!TextUtils.isEmpty(title)){
            TextView tvTitle = view.findViewById(R.id.tv_title);
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(title);
        }

        RadioGroup radioGroup = view.findViewById(R.id.radio_group);

        int size = itemArray.length;
        for (int index = 0; index < size; index++){
            RadioButton radioButton = (RadioButton) View.inflate(getContext(), R.layout.basic_lib_item_radio_button, null);
            radioButton.setId(index);
            radioGroup.addView(radioButton);
            RadioGroup.LayoutParams params = (RadioGroup.LayoutParams) radioButton.getLayoutParams();
            params.width = RadioGroup.LayoutParams.MATCH_PARENT;
            params.height = RadioGroup.LayoutParams.WRAP_CONTENT;
            radioButton.setLayoutParams(params);
            radioButton.setText(itemArray[index]);
            String itemValue = itemValueArray[index];
            radioButton.setTag(itemValue);
            if (TextUtils.equals(defaultValue, itemValue)){
                radioButton.setChecked(true);
                mSelectedIndex = index;
                mSelectedValue = itemValue;
            }
            final int finalIndex = index;
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String value = (String) v.getTag();
                    mSelectedIndex = finalIndex;
                    mSelectedValue = value;
                    if (confirmListener != null){
                        return;
                    }
                    if (mSelectedListener != null){
                        mSelectedListener.onCallback(finalIndex, value);
                    }
                    XLog.d(TAG,"onChecked index = " + finalIndex + " value = " +  value);
                    dismiss();
                }
            });
        }

        if (!TextUtils.isEmpty(cancelStr)) {
            Button btnCancel = view.findViewById(R.id.btn_cancel);
            btnCancel.setVisibility(View.VISIBLE);
            btnCancel.setText(cancelStr);
            btnCancel.setTextColor(ContextCompat.getColor(getContext(), cancelTextColor));
            btnCancel.setBackgroundResource(cancelBtnBgRes);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (confirmListener != null){
                        confirmListener.onCallback(mSelectedIndex, mSelectedValue);
                    }
                    dismiss();
                }
            });
        }

    }

    private int mSelectedIndex = -1;
    private String mSelectedValue;


    public static class Builder {
        private String title;

        private String cancelStr;
        @ColorRes
        private int cancelTextColor;
        private int cancelBtnBgRes;

        /**
         * 对话框是否可以取消
         */
        private boolean isCancelable;


        /**
         * 元素数组（用于显示）
         */
        String[] itemArray;
        /**
         * 元素值数组（用于存储）
         * 元素值数组跟元素数组 一一对应
         */
        String[] itemValueArray;
        /**
         * 默认值
         */
        String defaultValue;

        /**
         * 选中监听器
         */
        ResultListener<String> selectedListener;

        /**
         * 确认监听器
         */
        ResultListener<String> confirmListener;

        public Builder setConfirmListener(ResultListener<String> confirmListener) {
            this.confirmListener = confirmListener;
            return this;
        }

        public Builder setSelectedListener(ResultListener<String> selectedListener) {
            this.selectedListener = selectedListener;
            return this;
        }

        public Builder setDefaultValue(String defaultValue) {
            this.defaultValue = defaultValue;
            return this;
        }

        public Builder setItemValueArray(String[] itemValueArray) {
            this.itemValueArray = itemValueArray;
            return this;
        }

        public Builder setItemArray(String[] itemArray) {
            this.itemArray = itemArray;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setCancelText(String cancelStr) {
            this.cancelStr = cancelStr;
            return this;
        }

        public Builder setCancelTextColor(int cancelTextColor) {
            this.cancelTextColor = cancelTextColor;
            return this;
        }

        public Builder setCancelBtnBgRes(int cancelBtnBgRes) {
            this.cancelBtnBgRes = cancelBtnBgRes;
            return this;
        }

        public Builder setIsCancelable(boolean isCancelable) {
            this.isCancelable = isCancelable;
            return this;
        }



        public RadioDialogFragment create() {
            return new RadioDialogFragment(title
                    , cancelStr, cancelTextColor, cancelBtnBgRes
                    , isCancelable
                    , itemArray, itemValueArray, defaultValue
                    , selectedListener
                    , confirmListener);
        }
    }


}
