package com.example.android_demo.utils;


import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeUtils {

    public static String Format_TIME  = "yyyy.MM.dd HH:mm";
    public static String Format_TIME1 = "yyyy-MM-dd HH-mm-ss";
    public static String Format_TIME2 = "yyyy年MM月dd日";
    public static String Format_TIME3 = "yyyy-MM-dd";
    public static String Format_TIME4 = "yyyy-MM-dd-HH-mm-ss";
    public static String Format_TIME5 = "yyyy-MM-dd HH:mm";
    public static String Format_TIME7 = "yyyy-MM-dd HH-mm";
    public static String Format_TIME8 = "yyyy-MM-dd HH:mm:ss";
    public static String Format_TIME9 = "MM月dd日 HH:mm";
    public static String Format_TIME10 = "HH:mm:ss";
    public static String Format_TIME11 = "HH-mm";
    public static String Format_TIME12 = "MM-dd HH:mm";
    public static String Format_TIME13 = "yyyyMMddHHmmss";
    public static String Format_TIME14 = "yyyyMMddHHmmssSSS";
    public static String Format_TIME15 = "yyyy/MM/dd HH:mm";
    public static String Format_TIME16 = "yyyy/MM/dd HH:mm:ss";

    public static String stampToTime_1(String timeStamp){
        return stampToDate(timeStamp,Format_TIME1);
    }

    /**
     *  得到当前时间转换成String
     *
     * @param format  yyyy年MM月dd日 HH:mm:ss
     * @return
     */
    public static String getCurrentTime(String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        return formatter.format(curDate);
    }


    /**
     * 将时间戳转换为日期
     *
     * @param timeStamp  时间戳
     * @param dateFormat ，转换后的格式
     * @return String
     */
    public static String stampToDate(String timeStamp, String  dateFormat) {
        String dateStr = "";
        try {
            long ctime = 0;
            if (timeStamp != null)
                ctime = Long.parseLong(timeStamp);
            if (ctime % 1000 != 0)
                ctime *= 1000;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
            Date date = new Date(ctime);
            dateStr = simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateStr;
    }


    /**
     ** 将日期转换为时间戳
     * @param dateStr

     * @return
     */
    public static Date dataToStamp2(String dateStr) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Date date = null;

        try {
            date = sdr.parse(dateStr);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     ** 将日期转换为时间戳
     * @param dateStr
     * @param format
     * @return
     */
    public static String dataToStamp(String dateStr, String format) {
        SimpleDateFormat sdr = new SimpleDateFormat(format, Locale.CHINA);
        Date date;
        String times = null;
        try {
            date = sdr.parse(dateStr);
            long l = date.getTime();
            String stf = String.valueOf(l);
            times = stf.substring(0, 10);
//                  Log.d("--444444---", times);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return times;
    }


    /**
     ** 将日期转换为时间戳
     * @param dateStr
     * @param format
     * @return
     */
    public static String dataToStamp3(String dateStr, String format) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Date date;
        String times = null;
        try {
            date = sdr.parse(dateStr);
            long l = date.getTime();
            String stf = String.valueOf(l);
            times = stf.substring(0, 10);
//                  Log.d("--444444---", times);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return times;
    }

    /**
     * p判断 2个时间戳是否属于同一天
     *
     * @param data1
     * @param data2
     * @return
     */
    public static boolean isTheSameDay(String data1, String data2) {
        if (stampToDate(data1, Format_TIME3).equals(stampToDate(data2, Format_TIME3)))
            return true;
        else
            return false;

    }


    /**
     * 时:分:秒解析
     *
     * @param millisecond
     * @return
     */
    public static String MyFormatTime11(long millisecond) {
        StringBuilder sb = new StringBuilder();
        if (millisecond > 3600) {
            long h = millisecond / 3600;
            millisecond %= 3600;
            if (h < 10) sb.append("0");
            sb.append(h);
        } else sb.append("00");
        sb.append(":");
        if (millisecond > 60) {
            long m = millisecond / 60;
            millisecond %= 60;
            if (m < 10) sb.append("0");
            sb.append(m);
        } else sb.append("00");
        sb.append(":");
        if (millisecond < 10) sb.append("0");
        sb.append(millisecond);
        return sb.toString();
    }

    private String getTimeFormat(String time){
        String format = "";
        if(time.equals(Format_TIME))
            format = Format_TIME1;
        if(time.equals(Format_TIME1))
            format = Format_TIME1;
        if(time.equals(Format_TIME2))
            format = Format_TIME1;
        if(time.equals(Format_TIME3))
            format = Format_TIME1;
        if(time.equals(Format_TIME4))
            format = Format_TIME1;
        if(time.equals(Format_TIME5))
            format = Format_TIME1;
        if(time.equals(Format_TIME7))
            format = Format_TIME1;
        if(time.equals(Format_TIME8))
            format = Format_TIME1;
        if(time.equals(Format_TIME9))
            format = Format_TIME1;
        if(time.equals(Format_TIME10))
            format = Format_TIME1;
        if(time.equals(Format_TIME11))
            format = Format_TIME1;
        if(time.equals(Format_TIME12))
            format = Format_TIME1;


        return format;
    }

    /***
     * 获取当前日期距离过期时间的日期差值
     * @param endTime
     * @return String
     */
    public static String dateDiff(String endTime) {


        String strTime = null;
        // 按照传入的格式生成一个simpledateformate对象
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数
        long diff;
        long day = 0;
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = sd.format(curDate);
        try {
            long ct = sd.parse(str).getTime();
            long et =  sd.parse(endTime).getTime();
            // 获得两个时间的毫秒时间差异
            diff = sd.parse(str).getTime() - sd.parse(endTime).getTime()
            ;
            day = diff / nd;// 计算差多少天
            long hour = diff % nd / nh;// 计算差多少小时
            long min = diff % nd % nh / nm;// 计算差多少分钟
            long sec = diff % nd % nh % nm / ns;// 计算差多少秒
            // 输出结果
            if (day >= 1) {
                strTime = day + "天前";
            } else {

                if (hour >= 1) {
                    strTime = /*day + "天" +*/ hour + "时前" /*+ min + "分"*/;

                } else {
                    if (sec >= 1) {
                        strTime =/* day + "天" + hour + "时" +*/ min + "分" + sec + "秒前";
                    } else {
                        strTime = "刚刚";
                    }
                }
            }

            return strTime;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;

    }


    public static String timeLongToString(long millisecond) {
        if (millisecond < 60)
            return millisecond + "秒";
        else if (millisecond < 60 * 60)
            return millisecond / 60 + "分" + (millisecond - millisecond / 60 * 60 == 0 ? "" : timeLongToString(millisecond - millisecond / 60 * 60));
        else if (millisecond < 60 * 60 * 24)
            return millisecond / (60 * 60) + "时" + (millisecond - millisecond / (60 * 60) * 60 * 60 == 0 ? "" : timeLongToString(millisecond - millisecond / (60 * 60) * 60 * 60));
        else
            return millisecond / (60 * 60 * 24) + "天" + (millisecond - millisecond / (60 * 60 * 24) * 60 * 60 * 24 == 0 ? "" : timeLongToString(millisecond - millisecond / (60 * 60 * 24) * 60 * 60 * 24));
    }

    private static String getHour(long millisecond) {
        return millisecond / (60 * 60) + "时";
    }

    private static String getMinute(long millisecond) {
        millisecond = millisecond - 60 * 60 * (millisecond / 60 / 60);
        return millisecond / 60 + "分";
    }

    private static String getSecond(long millisecond) {
        return millisecond % 60 % 60 + "秒";
    }

    public static String getHourMinuteSecond(long millisecond) {
        if (millisecond == 0) return "0时0分0秒";
        return getHour(millisecond) + getMinute(millisecond) + getSecond(millisecond);
    }

    // * 将时间戳转换为16进制/////
    public static String stampToHex(int s) {
        String hexString;
        hexString = String.format("%08X", s);
        return hexString;
    }

    public static boolean compireTime(String currentTime, String time) {
        java.text.DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(df.parse(currentTime));
            c2.setTime(df.parse(time));
        } catch (ParseException e) {
            System.err.println("格式不正确");
        }
        int result = c1.compareTo(c2);
        if (result < 0)
            return false;
        else
            return true;//c1>=c2
//		else if(result==0)
//		 	return true;
//		return false;
    }

    public static boolean compireTime2(String currentTime, String time) {
        java.text.DateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(df.parse(currentTime));
            c2.setTime(df.parse(time));
        } catch (ParseException e) {
            System.err.println("格式不正确");
        }
        int result = c1.compareTo(c2);
        if (result < 0)
            return false;
        else
            return true;//c1>=c2
//		else if(result==0)
//		 	return true;
//		return false;
    }


    public static String getMD5Str(String str) {
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("MD5");

            messageDigest.reset();

            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException caught!");
            System.exit(-1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }

        return md5StrBuff.toString();
    }

    public static long stringToLong(String str) {
        return new Long(str);
    }



    public static final int SECONDS_IN_DAY = 60 * 60 * 24;
    public static final long MILLIS_IN_DAY = 1000L * SECONDS_IN_DAY;

    public static boolean isSameDayOfMillis(final long ms1, final long ms2) {
        final long interval = ms1 - ms2;
        return interval < MILLIS_IN_DAY
                && interval > -1L * MILLIS_IN_DAY
                && toDay(ms1) == toDay(ms2);
    }
    private static long toDay(long millis) {
        return (millis + TimeZone.getDefault().getOffset(millis)) / MILLIS_IN_DAY;
    }


    /* ==============================时间选择框=================================*/
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");

    public static String getToDay() {
        Date date = new Date();
        return simpleDateFormat.format(date);
    }
    /**
     * 得到几天前的时间
     *
     * @param data
     * @param day
     * @return
     */
    public static String getDateBefore(String data, int day) {
        Date date;
        try {
            date = simpleDateFormat.parse(data);
            Calendar now = Calendar.getInstance();
            now.setTime(date);
            now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
//            MyLogger.jLog().e("==data==" + data + "====" + simpleDateFormat.format(now.getTime().getTime()));
            return simpleDateFormat.format(now.getTime().getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";

    }

    /**
     * 得到几天后的时间
     *
     * @param data
     * @param day
     * @return
     */
    public static String getDateAfter(String data, int day) {
        Date date;
        try {
            date = simpleDateFormat.parse(data);
            Calendar now = Calendar.getInstance();
            now.setTime(date);
            now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
//            MyLogger.jLog().e("==data==" + data + "====" + simpleDateFormat.format(now.getTime().getTime()));
            return simpleDateFormat.format(now.getTime().getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 获取前n天日期、后n天日期
     *
     * @param distanceDay 前几天 如获取前7天日期则传-7即可；如果后7天则传7
     * @return
     */
    public static String getOldDate(int distanceDay) {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) + distanceDay);
        Date endDate = null;
        try {
            endDate = dft.parse(dft.format(date.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d("前7天==",dft.format(endDate));
        return dft.format(endDate);
    }


    //得到当前时间上一月
    public static String getLastMonth() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); // 设置为当前时间
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // 设置为上一个月
        date = calendar.getTime();
        return format.format(date);
    }

    //得到当前时间上一月
    public static String getLastMonths() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); // 设置为当前时间
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // 设置为上一个月
        date = calendar.getTime();
        return format.format(date);
    }


    //得到当前时间 三个月
    public static String getLastMonth3() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); // 设置为当前时间
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 3); // 设置为上三个月
        date = calendar.getTime();
        return format.format(date);
    }

    //得到当前时间 当前
    public static String getDataMonth() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); // 设置为当前时间
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        date = calendar.getTime();
        return format.format(date);
    }


    /* ==============================时间选择框=================================*/

}
