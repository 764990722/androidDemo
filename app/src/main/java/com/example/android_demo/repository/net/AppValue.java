package com.example.android_demo.repository.net;

import android.annotation.SuppressLint;
import android.os.Environment;

/**
 * 创 建 人 PeaceJay
 * 创建时间 2020/6/10
 * 类 描 述：公共属性
 */
public class AppValue {

    /*头像等保存路径*/
    @SuppressLint("SdCardPath")
    public final static String userXIXPath = Environment.getExternalStorageDirectory() + "/android_demo/Huancun/Image/";
    /*缓存保存位置*/
    @SuppressLint("SdCardPath")
    public static final String HuanCunPath = Environment.getExternalStorageDirectory() + "/android_demo/Huancun/";
    @SuppressLint("SdCardPath")
    public static final String LogPath = Environment.getExternalStorageDirectory() + "/android_demo/Log/";


    public static String api_token = "";
    /*登录后保存用来自动登录*/
    public static String USER_Token = "";
    public static String USER_COOKIE = "";
    public static String YouM_App_key = "5ee2fbf6dbc2ec076dd46987";
    /*友盟秘钥*/
    public static String YouM_Mi_key = "WOzwXgalTSophL1YExVslNYanq0LmA7KbHTGe1h4amAkwgSbX3QPbCnL4LnwZMY7lfA3DeycLWS1NvyoqsSJvdv/4BWX3v3pk0UvobtHBsbm4YlUCOwrseiwSoYK+6TIz/Ns84TBOyEySz1kz3zagdETwxuUo1sT+IJoTymTVSS+vGBZxToqVkRYbWmqipm/G7fw5vwNUxd0a0EyM5SWITSTcfObs3keirVZiFGfhLzVp3iXl47uwzgqhigzo9kQqnO4ZAwNOmMS/Y0VwYpybA==";
    /*颜色ID*/
    public static String HomeFilterColor = "";
    /*城市ID*/
    public static String HomeCityID = "";
    /*用户名*/
    public static String UserName = "";
    /*用户头像*/
    public static String UserAvatar = "";
    /*用户手机号*/
    public static String UserPhone = "";
    /*商品 大小*/
    public static String UserNormId = "";
    /*商品 颜色*/
    public static String UserColorId = "";
    /*商品 ID*/
    public static String CommodityID = "";

    public static double unitPrice = 0;//单价
    public static double freight = 0;//运费
    public static double fittingPic = 0;//试衣费
    public static double sumPic = 0;//总价


}
