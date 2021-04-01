package com.example.android_demo.utils.FileCache;



import com.example.android_demo.App;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;

/**
 * 缓存工具类
 */
public class ConfigCacheUtil {

//    /**
//     * 获取缓存 - 外部储存
//     *
//     * @param url 访问网络的URL
//     * @return 缓存数据
//     */
//    public static String getUrlCache(String url) {
//        if (url == null) {
//            return null;
//        }
//        String result = null;
//        String path = AppValue.ACachePath + MD5Utils.md5Encrypt(url) + ".txt";
//        File file = new File(path);
//        if (file.exists() && file.isFile()) {
//            try {
//                result = FileUtils.readTextFile(file);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return result;
//    }
//
//    /**
//     * 设置缓存 - 外部储存
//     *
//     * @param data
//     * @param url
//     */
//    public static void setUrlCache(String data, String url) {
//        if (TextUtils.isEmpty(AppValue.ACachePath)) {
//            return;
//        }
//        File dir = new File(AppValue.ACachePath);
//        if (!dir.exists() && Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//            dir.mkdirs();
//        }
//        File file = new File(AppValue.ACachePath + MD5Utils.md5Encrypt(url) + ".txt");
//        try {
//            // 创建缓存数据到磁盘，就是创建文件
//            FileUtils.writeTextFile(file, data);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    /**
     * 获取缓存 - 应用内路径
     *
     * @param url 访问网络的URL
     * @return 缓存数据
     */
    public static String getUrlCache(String url) {
        if (url == null) {
            return null;
        }
        String result = null;
        String path = App.ins().getFilesDir() + "/" + MD5Utils.md5Encrypt(url);
        File file = new File(path);
        if (file.exists() && file.isFile()) {
            try {
                result = FileUtils.readTextFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 设置缓存 - 应用内路径
     *
     * @param data
     * @param url
     */
    public static void setUrlCache(String data, String url) {
        FileOutputStream out = null;
        try {
            out = App.ins().openFileOutput(MD5Utils.md5Encrypt(url), MODE_PRIVATE);
            out.write(data.getBytes());
            out.flush();// 清理缓冲区的数据流
            out.close();// 关闭输出流
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
