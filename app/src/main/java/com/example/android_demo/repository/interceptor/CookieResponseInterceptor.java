package com.example.android_demo.repository.interceptor;

import com.example.android_demo.repository.net.AppValue;
import com.example.android_demo.repository.net.MCache;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import java.util.List;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by: var_rain.
 * Created date: 2020/8/9.
 * Description: Cookie返回拦截器
 */
public class CookieResponseInterceptor implements Interceptor {

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        List<String> headers = response.headers("Set-Cookie");
        for (String header : headers) {
            if (header.contains("JSESSIONID")) {
                String[] cookies = header.split(";");
                for (String cookie : cookies) {
                    if (cookie.contains("JSESSIONID")) {
                        MCache.put(AppValue.USER_COOKIE, cookie.trim());
                        break;
                    }
                }
            }
        }
        return response;
    }
}
