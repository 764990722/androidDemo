package com.example.android_demo.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.android_demo.App;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class Utils {

    private static Disposable subscribe;

    /**
     * 去除字符串中的符号
     *
     * @param str 字符串
     * @return 返回格式化的字符串
     */
    public static String clearString(String str) {
        String dest = null;
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static int dp2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, App.ins().getResources().getDisplayMetrics());
    }

    /**
     * 判断手机号是否有效
     * @param string
     * @return
     */
    public static boolean checkPhone(String string) {
        String regEx = "^1\\d{10}$";
        Pattern p = Pattern.compile(regEx);
        return p.matcher(string).matches();
    }

    /**
     * 判断字符串是否为空
     * */
    public static boolean isEmpty(String s) {
        if (null == s)
            return true;
        if (s.length() == 0)
            return true;
        if (s.trim().length() == 0)
            return true;
        return false;
    }

    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        //显示软键盘
//        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        //如果上面的代码没有弹出软键盘 可以使用下面另一种方式
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, 0);
    }


    /**
     * 通过反射的方式获取状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取现在时间
     * @return 返回短时间字符串格式yyyy-MM-dd
     */
    @SuppressLint("SimpleDateFormat")
    public static String getStringDateShort(Long time) {
        //Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(time);
        return dateString;
    }

    /**
     * 获取现在时间
     * @return 返回短时间字符串格式yyyy-MM-dd
     */
    @SuppressLint("SimpleDateFormat")
    public static String getStringDateShorts(Long time) {
        //Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
        String dateString = formatter.format(time);
        return dateString;
    }

    /**
     * 获取现在时间
     * @return 返回短时间字符串格式yyyy-MM-dd
     */
    @SuppressLint("SimpleDateFormat")
    public static String getStringDateShortDetailed(Long time) {
        //Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        String dateString = formatter.format(time);
        return dateString;
    }

    /*判断开始时间 结束时间*/
    public static int getTimeCompareSize(String startTime, String endTime){
        int i=0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");//年-月-日 时-分
        try {
            Date date1 = dateFormat.parse(startTime);//开始时间
            Date date2 = dateFormat.parse(endTime);//结束时间
            // 1 结束时间小于开始时间 2 开始时间与结束时间相同 3 结束时间大于开始时间
            if (date2.getTime()<date1.getTime()){
                i= 1;
            }else if (date2.getTime()==date1.getTime()){
                i= 2;
            }else if (date2.getTime()>date1.getTime()){
                //正常情况下的逻辑操作.
                i= 3;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  i;
    }

    //判断是否小米
    public static boolean isMIUI() {
        String manufacturer = Build.MANUFACTURER;
        if ("xiaomi".equalsIgnoreCase(manufacturer)) {
            return true;
        }
        return false;
    }


    //判断是否Google
    public static boolean isGoogle() {
        String manufacturer = Build.MANUFACTURER;
        if ("Google".equalsIgnoreCase(manufacturer)) {
            return true;
        }
        return false;
    }


    //判断是否vivo
    public static boolean isVivo() {
        String manufacturer = Build.MANUFACTURER;
        if ("vivo".equalsIgnoreCase(manufacturer)) {
            return true;
        }
        return false;
    }

    //判断是否HuaWei
    public static boolean isHuawei() {
        String manufacturer = Build.MANUFACTURER;
        if ("huawei".equalsIgnoreCase(manufacturer)) {
            return true;
        }
        return false;
    }


    /**
     * 获取手机IMEI号((International Mobile Equipment Identity,国际移动身份识别码)
     */
    @SuppressLint("MissingPermission")
    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        String deviceId = "";
        if (Build.VERSION.SDK_INT > 28) {
            //android 10以上已经获取不了imei了 用 android id代替
            if(TextUtils.isEmpty(deviceId)){
                deviceId = Settings.System.getString(
                        context.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
        } else {
            deviceId = telephonyManager.getDeviceId();
        }
        return  deviceId;
    }

    public static int dp2px(Context context, float dipValue) {
        try {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dipValue * scale + 0.5f);
        } catch (Exception e) {
            return (int) dipValue;
        }
    }
}
