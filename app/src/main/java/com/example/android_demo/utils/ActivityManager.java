package com.example.android_demo.utils;

/**
 * 创 建 人 PeaceJay
 * 创建时间 2019/5/28
 * 类 描 述：
 */

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:wang_sir
 * Time:2018/6/7 20:15
 * Description:This is ActivityManager
 */
public class ActivityManager {

    private List<Activity> activityList = new ArrayList<>();

    private ActivityManager() {
    }

    public static ActivityManager getInstance() {

        return ActivityManagerHolder.Instantce;
    }

    /**
     * 静态内部类获取单例
     */
    static class ActivityManagerHolder {
        public static ActivityManager Instantce = new ActivityManager();

    }

    /**
     * 添加activity
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (!activityList.contains(activity)) {
            activityList.add(activity);
        }

    }

    /**
     * 移除activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        if (activityList.contains(activity)) {
            activityList.remove(activity);
        }
    }

    /**
     * 关闭所有的activity，退出应用
     */
    public void finishActivity() {
        if (activityList != null && !activityList.isEmpty()) {
            for (Activity activity1 : activityList) {
                Log.i("TAG", "finishActivity: " + activity1.toString());
                //主页面是重用界面，不关闭
                if (!activity1.toString().contains("MainActivity")) {
                    activity1.finish();
                }
            }
            activityList.clear();
        }
    }

    /**
     * 关闭指定activity
     */
    public void closeActivity(String activityStr) {
        if (activityList != null && !activityList.isEmpty()) {
            for (int i = 0; i < activityList.size(); i++) {
                if (activityList.get(i).toString().contains(activityStr)) {
                    activityList.get(i).finish();// 关掉指定页面
                    activityList.remove(i);// 从集合中移除
                }
            }
        }
    }

}
