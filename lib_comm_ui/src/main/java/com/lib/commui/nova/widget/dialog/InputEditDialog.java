package com.lib.commui.nova.widget.dialog;

import android.text.InputFilter;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lib.commui.R;
import com.lib.commui.nova.util.KeyboardUtils;
import com.lib.commui.nova.util.XLog;
import com.lib.commui.nova.widget.dialog.base.BaseDialogFragment;
import com.lib.commui.nova.widget.dialog.base.ResultListener;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;

/**
 * 输入编辑弹窗
 */

public class InputEditDialog extends BaseDialogFragment {

    private static final String TAG = InputEditDialog.class.getSimpleName();

    public static class Builder {
        private String title;

        private String editText;
        private String editHintText;
        private int editInputType;
        /**
         * 限制输入文字的长度
         */
        private int editTextLength;

        private String cancelText;
        @ColorRes
        private int cancelTextColor;
        private int cancelBtnBgRes;
        private View.OnClickListener cancelListener;

        private String confirmText;
        @ColorRes
        private int confirmTextColor;
        private int confirmBtnBgRes;
        private ResultListener<String> confirmListener;

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setEditText(String text) {
            this.editText = text;
            return this;
        }

        public Builder setEditHintText(String editHintText) {
            this.editHintText = editHintText;
            return this;
        }

        public Builder setEditTextLength(int editTextLength) {
            this.editTextLength = editTextLength;
            return this;
        }

        public Builder setEditInputType(int editInputType) {
            this.editInputType = editInputType;
            return this;
        }

        public Builder setConfirmText(String confirmText) {
            this.confirmText = confirmText;
            return this;
        }

        public Builder setConfirmTextColor(int confirmTextColor) {
            this.confirmTextColor = confirmTextColor;
            return this;
        }

        public Builder setConfirmBtnBgRes(int confirmBtnBgRes) {
            this.confirmBtnBgRes = confirmBtnBgRes;
            return this;
        }

        public Builder setConfirmListener(ResultListener<String> confirmListener) {
            this.confirmListener = confirmListener;
            return this;
        }

        public Builder setCancelText(String cancelText) {
            this.cancelText = cancelText;
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

        public Builder setCancelListener(View.OnClickListener cancelListener) {
            this.cancelListener = cancelListener;
            return this;
        }

        public InputEditDialog create(){
            return new InputEditDialog(title, editText, editHintText, editInputType, editTextLength
                    , cancelText, cancelTextColor, cancelBtnBgRes, cancelListener
                    , confirmText, confirmTextColor, confirmBtnBgRes, confirmListener);
        }
    }

    private InputEditDialog(String title, String editText, String editHintText, int editInputType, int editTextLength
            , String cancelText, int cancelTextColor, int cancelBtnBgRes, View.OnClickListener cancelListener
            , String confirmText, int confirmTextColor, int confirmBtnBgRes, ResultListener<String> confirmListener) {
        this.title = title;
        this.editText = editText;
        this.editHintText = editHintText;
        this.editInputType = editInputType;
        this.editTextLength = editTextLength;
        this.cancelText = cancelText;
        this.cancelTextColor = cancelTextColor;
        this.cancelBtnBgRes = cancelBtnBgRes;
        this.cancelListener = cancelListener;
        this.confirmText = confirmText;
        this.confirmTextColor = confirmTextColor;
        this.confirmBtnBgRes = confirmBtnBgRes;
        this.confirmListener = confirmListener;
    }

    @Override
    public int getLayoutId() {
        return R.layout.basic_lib_dialog_input_edit;
    }

    @Override
    public int getDialogGravity() {
        return Gravity.CENTER;
    }

    @Override
    public int getDialogAnimation() {
        return R.style.basicLibCenterDialogAnimation;
    }

    private String title;

    private EditText etContent;
    private String editText;
    private String editHintText;
    private int editInputType;
    /**
     * 限制输入文字的长度
     * */
    private int editTextLength;

    private String cancelText;
    @ColorRes
    private int cancelTextColor;
    private int cancelBtnBgRes;
    private View.OnClickListener cancelListener;

    private String confirmText;
    @ColorRes
    private int confirmTextColor;
    private int confirmBtnBgRes;
    private ResultListener<String> confirmListener;

    @Override
    public void initView(View view) {
        if (!TextUtils.isEmpty(title)){
            TextView tvTitle = view.findViewById(R.id.tv_title);
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(title);
        }

        etContent = view.findViewById(R.id.et_content);
        etContent.setHint(editHintText);
        etContent.setInputType(editInputType);
        if (editTextLength != 0){
            etContent.setFilters(new InputFilter[]{new InputFilter.LengthFilter(editTextLength)});
        }

        etContent.setText(editText);
        if (!TextUtils.isEmpty(editText)){
            //将光标移至文字末尾
            etContent.setSelection(editText.length());
            //获得焦点即全选
            etContent.setSelectAllOnFocus(true);
        }
        etContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XLog.d(TAG,"et onClick");
                showKeyboard();
            }
        });

        Button btnCancel = view.findViewById(R.id.btn_cancel);
        if (!TextUtils.isEmpty(cancelText)) {
            btnCancel.setVisibility(View.VISIBLE);
            btnCancel.setText(cancelText);
            btnCancel.setTextColor(ContextCompat.getColor(getContext(), cancelTextColor));
            btnCancel.setBackgroundResource(cancelBtnBgRes);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cancelListener != null){
                        cancelListener.onClick(v);
                    }
                }
            });
        }

        Button btnConfirm = view.findViewById(R.id.btn_confirm);
        if (!TextUtils.isEmpty(confirmText)) {
            btnConfirm.setVisibility(View.VISIBLE);
            btnConfirm.setText(confirmText);
            btnConfirm.setTextColor(ContextCompat.getColor(getContext(), confirmTextColor));
            btnConfirm.setBackgroundResource(confirmBtnBgRes);
            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (confirmListener != null){
                        confirmListener.onCallback(0, etContent.getText().toString());
                    }
                }
            });
        }

    }

    public void showKeyboard() {
        XLog.d(TAG,"showKeyboard");
        KeyboardUtils.showSoftInput(etContent.getContext(), etContent);
    }

}
