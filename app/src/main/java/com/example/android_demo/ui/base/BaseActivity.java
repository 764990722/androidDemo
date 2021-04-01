package com.example.android_demo.ui.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.example.android_demo.R;
import com.example.android_demo.ui.base.listener.OnPermissionRequestListener;
import com.example.android_demo.utils.ActivityManager;
import com.example.android_demo.utils.StatusBar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by: PeaceJay
 * Created date: 2020/11/12.
 * Description: Activity父类
 */
@SuppressLint("Registered")
@SuppressWarnings("all")
public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity{

    private WeakReference<Activity> weakReference = null;
    private final int REQUEST_PERMISSION_CODE = 999;
    private int visibility = View.SYSTEM_UI_FLAG_VISIBLE;
    protected T binding;
    private boolean bool = true;


    private static final int FLAG_HOMEKEY_DISPATCHED = 0x80000000;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        );//不锁屏

        getWindow().setFlags(FLAG_HOMEKEY_DISPATCHED, FLAG_HOMEKEY_DISPATCHED);//关键代码


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StatusBar.setStatusBarColor(this, getResources().getColor(R.color.blue));
            StatusBar.setAndroidNativeLightStatusBar(this, false);
        }

        binding = DataBindingUtil.setContentView(this, onLayout());
        binding.setLifecycleOwner(this);
        onObject();
        onView();
        onData();
        //将activity添加到列表中
        if (weakReference == null) {
            weakReference = new WeakReference<Activity>(this);
        }
        ActivityManager.getInstance().addActivity(weakReference.get());
    }

    //关闭所有的activity
    public void finishActivity() {
        ActivityManager.getInstance().finishActivity();
    }

    /**
     * 初始化布局
     *
     * @return 返回布局资源id
     */
    protected abstract @LayoutRes
    int onLayout();

    /**
     * 初始化对象
     */
    protected abstract void onObject();

    /**
     * 初始化视图
     */
    protected abstract void onView();

    /**
     * 初始化数据
     */
    protected abstract void onData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (binding != null) {
            binding.unbind();
        }
        //将activity从列表中移除
        ActivityManager.getInstance().removeActivity(weakReference.get());
    }

    /**
     * 设置是否点击任意处隐藏键盘
     *
     * @param bool 需要显示的标题名
     */
    public void setBool(boolean bool) {
        this.bool = bool;
    }

    /*点击任意位置隐藏软键盘*/
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (bool) {
                View v = getCurrentFocus();
                if (isShouldHideInput(v, ev)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
                return super.dispatchTouchEvent(ev);
            }
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


}
