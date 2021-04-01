package com.example.android_demo.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.android_demo.R;
import com.example.android_demo.repository.net.AppValue;
import com.example.android_demo.repository.net.MCache;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by: PeaceJay
 * Created date: 2020/11/13.
 * Description: 加载框 - 懒汉式
 */
public class Loading {

    private Disposable subscribe;
    private static volatile Loading instance;
    private Dialog dialog;
    @SuppressLint("StaticFieldLeak")
    private TextView tv_name;
    Handler handler = new Handler();
    private int time = 30;
    private String cont;


    private Loading() {}

    public static Loading getInstance() {
        if (instance == null) {
            synchronized (Loading.class) {
                if (instance == null) {
                    instance = new Loading();
                }
            }
        }
        return instance;
    }


    /**
     * 显示加载动画
     */
    public void show(String cont, Activity context) {
        time = 30;
        this.cont = cont;
        if (dialog == null) {
            dialog = new Dialog(context, R.style.dialog1);
            View viewDialog = LayoutInflater.from(context).inflate(R.layout.progress_dialog, null);
            //viewDialog.setBackgroundColor(0x7f000000);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            //是否物理返回
            dialog.setCancelable(false);
            dialog.setContentView(viewDialog);
            /*触摸屏幕其它区域，就会让这个progressDialog消失  消失true反之false*/
            dialog.setCanceledOnTouchOutside(false);
            tv_name = viewDialog.findViewById(R.id.tv_name);
//            handler.postDelayed(runnable, 0);
            start(context, cont);
        }
        dialog.show();
    }

    /**
     * 隐藏加载动画
     */
    public void hide() {
        if (dialog != null) {
            shop();
            try {
                dialog.dismiss();
                dialog = null;
            } catch (Exception ignored) {
            }
        }
    }


    //time 轮询倒计时
    public void start(Activity context, String cont) {
        //有限制次数的轮询器 interval对应参数 ：首次执行延时时间 、 每次轮询间隔时间 、 时间类型
        //+ 2 为了显示最大与最小0停顿
        subscribe = Observable.intervalRange(0, time + 2, 0, 1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
//                        aLong = aLong;//显示到0结束
//                        Log.e("ccb", "accept: " + aLong);
//                        Log.e("ccb", "accept: " + (time - aLong));
                        long aLongs = time - aLong;
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (TextUtils.isEmpty(cont)) {
                                    tv_name.setText(String.format("加载中... %s秒", aLongs));
                                } else {
                                    tv_name.setText(String.format("%s %s秒", cont, aLongs));
                                }
                                if (aLongs < 0) {
                                    hide();
                                }
                            }
                        });
                    }
                });
    }

    //结束轮询
    public void shop() {
        if (subscribe == null) return;
        //停止轮询，销毁这个Subscribe;
        if (!subscribe.isDisposed()) subscribe.dispose();
    }


    //处理倒计时
    private final Runnable runnable = new Runnable() {
        @SuppressLint("SetTextI18n")
        public void run() {
            if (TextUtils.isEmpty(cont)) {
                tv_name.setText(String.format("加载中... %s秒", time));
            } else {
                tv_name.setText(String.format("%s %s秒", cont, time));
            }
            handler.postDelayed(this, 1000);
            if (time < 0) {
                hide();
            }
            time--;//会在这里在减去1
        }
    };

}
