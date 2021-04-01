package com.example.android_demo.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.android_demo.R;


/**
 * Created by: PeaceJay
 * Created date: 2020/11/20
 * Description: 对话框提示
 */


public class ReturnDialog {

    private Dialog mDialog;
    @SuppressLint("StaticFieldLeak")
    private static ReturnDialog instance;
    private ReturnDialog.ButtonPositiveView buttonPositiveView;
    private ReturnDialog.ButtonNegativeView buttonNegativeView;

    private ReturnDialog() {}

    public static ReturnDialog getInstance() {
        if (instance == null) {
            synchronized (ReturnDialog.class) {
                if (instance == null) {
                    instance = new ReturnDialog();
                }
            }
        }
        return instance;
    }



    /**
     * 按钮点击事件需要的方法
     */
    public void buttonPosiSetOnclick(ReturnDialog.ButtonPositiveView buttonPositiveView) {
        this.buttonPositiveView = buttonPositiveView;
    }


    /**
     * 按钮点击事件需要的方法
     */
    public void buttonNegaSetOnclick(ReturnDialog.ButtonNegativeView buttonNegativeView) {
        this.buttonNegativeView = buttonNegativeView;
    }

    /**
     * 按钮点击事件对应的接口
     */
    public interface ButtonPositiveView {
        void onclick(View view);
    }

    /**
     * 按钮点击事件对应的接口
     */
    public interface ButtonNegativeView {
        void onclick();
    }


    public void loadDialog(Activity mContext,String cont){
        mDialog = new Dialog(mContext, R.style.dialog1);
        LayoutInflater in = LayoutInflater.from(mContext);
        View viewDialog = in.inflate(R.layout.retum_dialog, null);
        TextView contentView = viewDialog.findViewById(R.id.contentView);
        contentView.setText(cont);
        //viewDialog.setBackgroundColor(0x7f000000);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //true不禁止物理返回
        mDialog.setCancelable(true);
        mDialog.setContentView(viewDialog);
        /*触摸屏幕其它区域，就会让这个progressDialog消失  消失true反之false*/
        mDialog.setCanceledOnTouchOutside(false);
        if (!mContext.isFinishing()){
            //xActivity即为本界面的Activity
            mDialog.show();
        }

        viewDialog.findViewById(R.id.positiveView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                if (buttonPositiveView != null) {
                    //接口实例化后的而对象，调用重写后的方法
                    buttonPositiveView.onclick(v);
                }
            }
        });

        viewDialog.findViewById(R.id.negativeView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                if (buttonNegativeView != null) {
                    //接口实例化后的而对象，调用重写后的方法
                    buttonNegativeView.onclick();
                }
            }
        });

        mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                mDialog.dismiss();
                //默认点击外部、物理返回键 监听
                if (buttonNegativeView != null) {
                    //接口实例化后的而对象，调用重写后的方法
                    buttonNegativeView.onclick();
                }
            }
        });
    }

    public  void removeDialog() {
        if(mDialog!=null)
            mDialog.dismiss();
    }



}
