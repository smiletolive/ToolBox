package com.lib.commui.nova.widget.dialog;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lib.commui.R;
import com.lib.commui.nova.widget.dialog.base.BaseDialogFragment;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;


public class InfoDialogFragment extends BaseDialogFragment {

    private static final int INVALID_VALUE = -1;

    private String title;
    private String message;
    /**
     * 对齐方式
     */
    private int messageTextGravity = INVALID_VALUE;

    private String imageUrl;
    private String imageDesc;

    private String okStr;
    @ColorRes
    private int okTextColor;
    private int okBtnBgRes;
    private View.OnClickListener okListener;

    private String cancelStr;
    @ColorRes
    private int cancelTextColor;
    private int cancelBtnBgRes;
    private View.OnClickListener cancelListener;

    private boolean showCloseIcon;

    private boolean isCancelable;

    /**
     * 文本信息样式
     */
    private static final int DIALOG_TEXT_INFO_STYLE = 1;

    /**
     * 图片信息样式
     */
    private static final int DIALOG_IMAGE_INFO_STYLE = 2;


    /**
     * 创建实例
     * @param title 标题
     * @param message 内容文字
     * @param imageUrl 图片地址
     * @param imageDesc 图片描述
     * @param okStr 确定按钮的文字
     * @param okTextColor 确定按钮的文字颜色
     * @param okBtnBgRes 确定按钮背景颜色
     * @param okListener 确定监听事件
     * @param cancelStr 取消按钮的文字
     * @param cancelTextColor 取消按钮的文字颜色
     * @param cancelBtnBgRes 取消按钮背景颜色
     * @param cancelListener 取消监听事件，传入null表示没有并且不显示取消按钮
     * @param showCloseIcon 是否显示关闭图标
     * @param isCancelable 是否可以点击返回键或者按弹窗外部来关闭弹出框
     * @return 返回实例
     */
    protected InfoDialogFragment(String title,
                                 String message,
                                 int messageTextGravity,
                                 String imageUrl,
                                 String imageDesc,
                                 String okStr,
                                 @ColorRes int okTextColor,
                                 int okBtnBgRes,
                                 View.OnClickListener okListener,
                                 String cancelStr,
                                 @ColorRes int cancelTextColor,
                                 int cancelBtnBgRes,
                                 View.OnClickListener cancelListener,
                                 boolean showCloseIcon,
                                 boolean isCancelable) {
        this.title = title;
        this.message = message;
        this.messageTextGravity = messageTextGravity;
        this.imageUrl = imageUrl;
        this.imageDesc = imageDesc;

        this.okStr = okStr;
        this.okTextColor = okTextColor;
        this.okBtnBgRes = okBtnBgRes;
        this.okListener = okListener;

        this.cancelStr = cancelStr;
        this.cancelTextColor = cancelTextColor;
        this.cancelBtnBgRes = cancelBtnBgRes;
        this.cancelListener = cancelListener;

        this.showCloseIcon = showCloseIcon;
        this.isCancelable = isCancelable;
    }

    @Override
    public int getDialogGravity() {
        return Gravity.CENTER;
    }

    @Override
    public int getLayoutId() {
        return R.layout.basic_lib_dialog_text_info;
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


        if (!TextUtils.isEmpty(message)){
            TextView tvMessage = view.findViewById(R.id.tv_message);
            tvMessage.setVisibility(View.VISIBLE);
            tvMessage.setText(message);
            if (messageTextGravity != INVALID_VALUE){
                tvMessage.setGravity(messageTextGravity);
            }
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
                    if (cancelListener != null){
                        cancelListener.onClick(v);
                    }
                }
            });
        }

        if (!TextUtils.isEmpty(okStr)) {
            Button btnConfirm = view.findViewById(R.id.btn_confirm);
            btnConfirm.setVisibility(View.VISIBLE);
            btnConfirm.setText(okStr);
            btnConfirm.setTextColor(ContextCompat.getColor(getContext(), okTextColor));
            btnConfirm.setBackgroundResource(okBtnBgRes);
            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (okListener != null){
                        okListener.onClick(v);
                    }
                }
            });
        }

    }


    public static class Builder {
        private String title;
        private String message;
        /**
         * 对齐方式
         */
        private int messageTextGravity = INVALID_VALUE;

        private String imageUrl;
        private String imageDesc;
        private String okStr;
        private int okTextColor;
        private int okBtnBgRes;
        private View.OnClickListener okListener;
        private String cancelStr;
        private int cancelTextColor;
        private int cancelBtnBgRes;
        private View.OnClickListener cancelListener;
        private boolean showCloseIcon = false;
        /**
         * 对话框是否可以取消(true默认可以)
         */
        private boolean isCancelable = true;

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setMessageTextGravity(int gravity) {
            this.messageTextGravity = gravity;
            return this;
        }

        public Builder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder setImageDesc(String imageDesc) {
            this.imageDesc = imageDesc;
            return this;
        }

        public Builder setConfirmText(String okStr) {
            this.okStr = okStr;
            return this;
        }

        public Builder setConfirmTextColor(int okTextColor) {
            this.okTextColor = okTextColor;
            return this;
        }

        public Builder setConfirmBtnBgRes(int okBtnBgRes) {
            this.okBtnBgRes = okBtnBgRes;
            return this;
        }

        public Builder setConfirmListener(View.OnClickListener okListener) {
            this.okListener = okListener;
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

        public Builder setCancelListener(View.OnClickListener cancelListener) {
            this.cancelListener = cancelListener;
            return this;
        }

        public Builder setShowCloseIcon(boolean showCloseIcon) {
            this.showCloseIcon = showCloseIcon;
            return this;
        }

        public Builder setIsCancelable(boolean isCancelable) {
            this.isCancelable = isCancelable;
            return this;
        }

        public InfoDialogFragment create() {
            return new InfoDialogFragment(title
                    , message, messageTextGravity
                    , imageUrl, imageDesc
                    , okStr, okTextColor, okBtnBgRes, okListener
                    , cancelStr, cancelTextColor, cancelBtnBgRes, cancelListener
                    , showCloseIcon
                    , isCancelable);
        }
    }


}
