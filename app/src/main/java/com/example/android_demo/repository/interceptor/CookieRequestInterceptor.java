package com.example.android_demo.repository.interceptor;

import com.example.android_demo.repository.net.AppValue;
import com.example.android_demo.repository.net.MCache;
import com.example.android_demo.utils.Text;

import org.jetbrains.annotations.NotNull;
import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by: var_rain.
 * Created date: 2020/8/9.
 * Description: Cookie请求拦截器
 */
public class CookieRequestInterceptor implements Interceptor {

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        String cookie = MCache.get(AppValue.USER_Token, null);
        Request request = chain.request();
        if (Text.empty(cookie)) {
            return chain.proceed(request);
        } else {
            return chain.proceed(request.newBuilder().addHeader("Cookie", cookie).build());
        }
    }
}
