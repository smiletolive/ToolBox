package com.lib.commui.nova.widget.dialog;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.lib.commui.R;
import com.lib.commui.nova.widget.dialog.base.BaseDialogFragment;


/**
 * 进度对话框
 * Created on 2016/11/23.
 */

public class LoadingDialog extends BaseDialogFragment {

    private int imageId;

    private String message;
    /**
     * 对话框是否可以取消(默认可以)
     *  true 可以 false不可以
     */
    private boolean isCancelable = true;

    private boolean isLoadAnimation;

    public static class Builder {
        private int imageId;

        private String message;
        /**
         * 对话框是否可以取消(默认可以)
         *  true 可以 false不可以
         */
        private boolean isCancelable = true;

        private boolean isLoadAnimation;

        public Builder setImageId(int imageId) {
            this.imageId = imageId;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            isCancelable = cancelable;
            return this;
        }

        public Builder setLoadAnimation(boolean loadAnimation) {
            isLoadAnimation = loadAnimation;
            return this;
        }

        public LoadingDialog create(){
            return new LoadingDialog(imageId, message, isCancelable, isLoadAnimation);
        }
    }

    public LoadingDialog(int imageId, String message, boolean isCancelable, boolean isLoadAnimation) {
        this.imageId = imageId;
        this.message = message;
        this.isCancelable = isCancelable;
        this.isLoadAnimation = isLoadAnimation;
    }

    @Override
    public int getLayoutId() {
        return R.layout.basic_lib_dialog_loading;
    }

    @Override
    public int getDialogGravity() {
        return Gravity.CENTER;
    }

    @Override
    public int getDialogAnimation() {
        return R.style.basicLibCenterDialogAnimation;
    }

    @Override
    public int getDialogThemesStyleId() {
        return R.style.basicLibDialogNoMask;
    }

    @Override
    public boolean isDialogCancelable() {
        return isCancelable;
    }

    @Override
    public void initView(View view) {
        TextView textView = view.findViewById(R.id.tv_tip);
        textView.setText(message);
    }

    @Override
    public void onDestroyView() {
//        if(ivImage != null){
//            ivImage.clearAnimation();
//        }
        super.onDestroyView();
    }
}
