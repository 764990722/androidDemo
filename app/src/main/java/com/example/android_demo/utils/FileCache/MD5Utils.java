package com.example.android_demo.utils.FileCache;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * 功能说明：MD5加密的工具类
 */
public class MD5Utils {
//    private static String YAN = "wderer223sdeaaddr23wssdee334a";


    /*加密*/
    public static String md5Encrypt(String str){
        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] bys = digest.digest((str).getBytes());
            StringBuilder sb = new StringBuilder();
            for(byte bb : bys){
                int num = bb & 0xff;
                String hex = Integer.toHexString(num);
                if(hex.length() == 1){
                    sb.append("0");
                }
                sb.append(hex);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


//    /*解密*/
//    public static String stringToMD5(String plainText) {
//        byte[] secretBytes = null;
//        try {
//            secretBytes = MessageDigest.getInstance("md5").digest(
//                    plainText.getBytes());
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException("没有这个md5算法！");
//        }
//        String md5code = new BigInteger(1, secretBytes).toString(16);
//        for (int i = 0; i < 32 - md5code.length(); i++) {
//            md5code = "0" + md5code;
//        }
//        return md5code;
//    }



}