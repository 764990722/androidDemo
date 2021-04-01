package com.example.android_demo.repository.interceptor;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by: PeaceJay
 * Created date: 2020/12/07
 * Description: Header拦截器
 */
public class HeaderInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request().newBuilder()
//                .addHeader("api-version", "1.0.0")
//                .addHeader("api-token", "")
//                .addHeader("MsgVer", "V0001")//标识报文版本，取值 V0001
//                .addHeader("SndDt", TimeUtils.getCurrentTime(TimeUtils.Format_TIME8))//格式为 yyyy-mm-ddTHH:MM:SS
                .build();
        return chain.proceed(request);
    }
}
