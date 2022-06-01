package com.lib.commui.nova.widget.dialog.base;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.lib.commui.R;
import com.lib.commui.nova.util.XLog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseDialogFragment extends AppCompatDialogFragment {

    public final String TAG = "MF_" + getClass().getSimpleName();

    /**
     * 获取对话框局部
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 初始化视图
     * @param view
     */
    public abstract void initView(View view);

    /**
     * 获取对话框主题样式id （默认背景带蒙版样式），可重写改变样式
     * @return
     */
    public int getDialogThemesStyleId(){
        return R.style.basicLibDialogMask;
    }

    /**
     * 获取对话框显示位置 （默认在底部），可重写改变位置
     * @return
     */
    public int getDialogGravity(){
        return Gravity.BOTTOM;
    }

    /**
     * 获取对话框进入退出动画，可重写改变动画
     * @return
     */
    public int getDialogAnimation(){
        return R.style.basicLibBottomDialogAnimation;
    }

    /**
     * 对话框是否可以取消(默认可以)，可重写改变
     * @return true 可以 false不可以
     */
    public boolean isDialogCancelable(){
        return true;
    }

    /**
     * 获取对话框宽配置 默认是WindowManager.LayoutParams.WRAP_CONTENT
     * @return 默认是WRAP_CONTENT，如需要全屏显示对话框需要子类重写此类WindowManager.LayoutParams.MATCH_PARENT
     */
    public int getDialogWidth() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    private Unbinder unbinder;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        XLog.d(TAG,"onCreateDialog");
        Dialog dialog = new Dialog(getActivity(), getDialogThemesStyleId());
        View view = View.inflate(getContext(), getLayoutId(), null);
        unbinder = ButterKnife.bind(this, view);
        initView(view);
        dialog.setContentView(view);
        dialog.setCancelable(isDialogCancelable());
        dialog.setCanceledOnTouchOutside(isDialogCancelable());
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        //适应屏幕宽
        params.width = getDialogWidth();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        //显示位置，默认在底部
        window.setGravity(getDialogGravity());
        //去除内边框
        window.setBackgroundDrawableResource(android.R.color.transparent);
        //加进入退出动画
        window.setWindowAnimations(getDialogAnimation());
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        onInitWindowFlag(window);
        return dialog;
    }

    public void onInitWindowFlag(Window window) {
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        XLog.d(TAG,"onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        XLog.d(TAG,"onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        XLog.d(TAG,"onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        XLog.d(TAG,"onViewCreated");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        XLog.d(TAG,"onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        XLog.d(TAG,"onStart");
        listenerBackEvent();
    }

    @Override
    public void onResume() {
        super.onResume();
        XLog.d(TAG,"onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        XLog.d(TAG,"onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        XLog.d(TAG,"onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        XLog.d(TAG,"onDestroyView");
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        XLog.d(TAG,"onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        XLog.d(TAG,"onDetach");
    }

    /**
     * 监听返回事件
     */
    private void listenerBackEvent() {
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isDialogCancelable()) {
                    return true;
                }
                return false;
            }
        });
    }

}
