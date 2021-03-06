package com.example.android_demo.repository;

import com.example.android_demo.repository.interceptor.CookieRequestInterceptor;
import com.example.android_demo.repository.interceptor.CookieResponseInterceptor;
import com.example.android_demo.repository.interceptor.HeaderInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by: var_rain.
 * Created date: 2018/10/21.
 * Description: 网络访问工具类
 */
@SuppressWarnings("unused")
public class Network {

    // 网络接口
    private static ApiService API;
    // 请求地址
    private static String BASE_URL = ApiService.BASE_URL;
    // OkHttpClient
    private static OkHttpClient client;

    /**
     * 初始化方法,需要在{@link android.app.Application} 中初始化
     */
    public static void init() {
        Network.client = Network.initHttpClient("1.0.0");
        Network.API = Network.initApiService(Network.client);
    }

    /**
     * 初始化 {@link OkHttpClient}
     * @return {@link OkHttpClient}
     */
    private static OkHttpClient initHttpClient(String version) {
        return new OkHttpClient.Builder()
                .addInterceptor(new HeaderInterceptor())//设置Header拦截器
                .addInterceptor(new CookieRequestInterceptor())
                .addInterceptor(new CookieResponseInterceptor())
                .addInterceptor(getHttpLoggingInterceptor()) //显示日志
                .connectTimeout(ApiService.TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(ApiService.TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(ApiService.TIME_OUT, TimeUnit.SECONDS).build();
    }

    /**
     * 初始化网络请求接口
     * @param client {@link OkHttpClient}
     * @return {@link ApiService}
     */
    private static ApiService initApiService(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService.class);
    }

    /**
     * 打印接口日志
     */
    private static Interceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    /**
     * 下载文件
     *
     * @param url       下载地址
     * @param directory 保存目录
     * @param filename  保存文件名
     * @param listener  下载监听
     */
    public static void download(String url, String directory, String filename, DownloadListener listener) {
        Download download = new Download(directory, filename, url, Network.client, listener);
        download.start();
    }

    /**
     * 改变地址
     *
     * @param host 地址
     */
    public static void change(String host) {
        Network.BASE_URL = host;
        Network.init();
    }

    /**
     * 获取网路接口
     *
     * @return 返回一个ApiService接口对象
     */
    public static ApiService api() {
        return Network.API;
    }
}
