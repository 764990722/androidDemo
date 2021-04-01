package com.example.android_demo.utils;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.annotation.StringRes;
import androidx.databinding.DataBindingUtil;

import com.example.android_demo.App;
import com.example.android_demo.R;
import com.example.android_demo.databinding.ViewToastBinding;


/**
 * Created by: var_rain.
 * Created date: 2020/1/3.
 * Description: Toast工具
 */
@SuppressWarnings("all")
public class Toast {

    // 长时间显示
    public final static int TOAST_DURATION_LONG = 1;
    // 短时间显示
    public final static int TOAST_DURATION_SHORT = 0;

    /**
     * Toast对象
     */
    private static android.widget.Toast toast = null;
    /**
     * 之前显示的内容
     */
    private static String oldMsg;
    /**
     * 第一次时间
     */
    private static long oneTime = 0;
    /**
     * 第二次时间
     */
    private static long twoTime = 0;

    /**
     * 显示Toast
     *
     * @param text 字符串
     */
    public static void show(String message) {
        if (toast == null) {
//            toast = android.widget.Toast.makeText(App.ins().activity(), message, android.widget.Toast.LENGTH_SHORT);
//            toast.setGravity(Gravity.BOTTOM, 0, 300);
//            toast.show();

            toast = new android.widget.Toast(App.ins().activity());
            toast.setDuration(TOAST_DURATION_SHORT);
            toast.setGravity(Gravity.BOTTOM, 0, 300);
            ViewToastBinding binding = DataBindingUtil.inflate(LayoutInflater.from(App.ins().activity()), R.layout.view_toast, null, false);
            TextView mTextView = binding.tvToastText;
            toast.setView(binding.getRoot());
            mTextView.setText(message);
            toast.show();

            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (message.equals(oldMsg)) {
                if (twoTime - oneTime > android.widget.Toast.LENGTH_SHORT) {
                    toast.show();
                }
            } else {
                oldMsg = message;
//                toast.setText(message);
//                toast.show();

                ViewToastBinding binding = DataBindingUtil.inflate(LayoutInflater.from(App.ins().activity()), R.layout.view_toast, null, false);
                TextView mTextView = binding.tvToastText;
                toast.setView(binding.getRoot());
                mTextView.setText(message);
                toast.show();
            }
        }
        oneTime = twoTime;
        //ToastView.INS.showToast(text, Gravity.CENTER, Toast.TOAST_DURATION_SHORT);
    }






    /**
     * 显示Toast
     *
     * @param id 字符串资源ID
     */
    public static void show(@StringRes int id) {
        ToastView.INS.showToast(App.ins().getString(id), Gravity.CENTER, TOAST_DURATION_SHORT);
    }

    /**
     * 显示Toast
     *
     * @param id   字符串资源ID
     * @param args 格式化参数
     */
    public static void show(@StringRes int id, Object... args) {
        ToastView.INS.showToast(App.ins().getString(id, args), Gravity.CENTER, TOAST_DURATION_SHORT);
    }

    /**
     * 显示Toast
     *
     * @param text     字符串
     * @param gravity  显示位置,参考 {@link Gravity}
     * @param duration 显示时长,接受参数 {@link com.example.android_demo.utils.Toast#TOAST_DURATION_SHORT, Toast#TOAST_DURATION_LONG}
     */
    public static void show(String text, int gravity, int duration) {
        ToastView.INS.showToast(text, gravity, duration);
    }

    /**
     * 子线程中显示Toast
     *
     * @param text 字符串
     */
    public static void showSync(String text) {
        App.ins().activity().runOnUiThread(() -> Toast.show(text));
    }

    /**
     * 子线程中显示Toast
     *
     * @param id 字符串资源ID
     */
    public static void showSync(@StringRes int id) {
        App.ins().activity().runOnUiThread(() -> Toast.show(id));
    }

    /**
     * 子线程中显示Toast
     *
     * @param id   字符串资源ID
     * @param args 格式化参数
     */
    public static void showSync(@StringRes int id, Object... args) {
        App.ins().activity().runOnUiThread(() -> Toast.show(id, args));
    }

    /**
     * 子线程中显示Toast
     *
     * @param text     字符串
     * @param gravity  显示位置,参考 {@link Gravity}
     * @param duration 显示时长,接受参数 {@link com.example.android_demo.utils.Toast#TOAST_DURATION_SHORT, Toast#TOAST_DURATION_LONG}
     */
    public static void showSync(String text, int gravity, int duration) {
        App.ins().activity().runOnUiThread(() -> show(text, gravity, duration));
    }

    /**
     * Created by: var_rain.
     * Created time: 2020/1/3.
     * Description: Toast提示框
     */
    public enum ToastView {
        @SuppressLint("StaticFieldLeak") INS;

        /**
         * 显示Toast
         *
         * @param content  显示内容
         * @param gravity  显示位置
         * @param duration 显示时间
         */
        public void showToast(String content, int gravity, int duration) {
            android.widget.Toast mToast = new android.widget.Toast(App.ins().activity());
            mToast.setDuration(duration);
            mToast.setGravity(gravity, 0, 300);
            ViewToastBinding binding = DataBindingUtil.inflate(LayoutInflater.from(App.ins().activity()), R.layout.view_toast, null, false);
            TextView mTextView = binding.tvToastText;
            mToast.setView(binding.getRoot());
            mTextView.setText(content);
            mToast.show();
        }
    }
}
