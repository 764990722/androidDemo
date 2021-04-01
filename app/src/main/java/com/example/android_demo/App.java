package com.example.android_demo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.multidex.MultiDexApplication;

import com.example.android_demo.repository.Network;
import com.example.android_demo.repository.net.MCache;
import com.example.android_demo.worker.Worker;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: zhoujingjie
 * Created date: 2020/11/12
 * Description:
 */
public class App extends MultiDexApplication implements Application.ActivityLifecycleCallbacks {

    // 静态实例
    @SuppressLint("StaticFieldLeak")
    private static App INS;
    // 当前所在的Activity
    private Activity activity;
    // Activity管理集合
    private List<Activity> activities;

    @Override
    public void onCreate() {
        super.onCreate();
        Worker.init(Worker.newCachedThreadPool());
//        GlobalCrashCapture.ins().init(this);
        this.activities = new ArrayList<>();
        this.registerActivityLifecycleCallbacks(this);
        App.INS = this;
        Network.init();//初始化接口
        MCache.init(this);
    }

    /**
     * 获取静态实例
     *
     * @return 返回一个 {@link App} 对象
     */
    public static App ins() {
        return INS;
    }

    /**
     * 获取当前显示的Activity
     *
     * @return 返回一个 {@link Activity} 对象
     */
    public Activity activity() {
        return this.activity;
    }

    /**
     * 退出APP
     */
    @SuppressWarnings("unused")
    public void exit() {
        for (Activity act : activities) {
            if (act != null && !act.isFinishing()) {
                act.finish();
            }
        }
        activities.clear();
        Worker.destroy();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.color_ffe2d8, R.color.main_color);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }


    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        this.activities.add(activity);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        this.activities.remove(activity);
    }

}
