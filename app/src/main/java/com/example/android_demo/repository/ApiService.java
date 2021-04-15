package com.example.android_demo.repository;

import com.example.android_demo.BuildConfig;
import com.example.android_demo.repository.entity.receive.RUserList;
import com.example.android_demo.repository.entity.result.Result;
import com.example.android_demo.repository.entity.submit.SLogin;
import com.example.android_demo.repository.entity.submit.SRegister;
import com.example.android_demo.repository.entity.submit.SUpData;
import com.example.android_demo.repository.net.NetParmet;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by: var_rain.
 * Created date: 2018/10/21.
 * Description: 服务器接口映射
 */
public interface ApiService {

    // 服务器地址
    String BASE_URL = BuildConfig.SERVER_BASE;
    // 超时 (秒)
    int TIME_OUT = 15;

    /**
     * 用户登录
     */
    @POST("user/login")
    Observable<Result<Object>> login(@Body SLogin body);

    /**
     * 用户注册
     */
    @POST("user/register")
    Observable<Result<Object>> register(@Body SRegister body);

    /**
     * 查询用户列表
     */
    @GET("user/queryUser")
    Observable<Result<RUserList>> queryUser();


    /**
     * 查询用户列表 - 分页
     *
     * @param pageNum  第几页
     * @param pageSize 每页条数
     * @param username 姓名条件查询
     */
    @GET("user/getUserPageList")
    Observable<Result<RUserList>> getUserPageList(@Query("pageNum") Integer pageNum,
                                                  @Query("pageSize") Integer pageSize,
                                                  @Query("username") String username);


    /**
     * 修改用户信息
     * 并上传头像
     */
    @POST("user/updateUser")
    Observable<Result<Object>> updateUser(@Body SUpData body);


    /**
     * 删除用户
     */
    @POST("user/deleteUser")
    Observable<Result<Object>> deleteUser(@Body SUpData body);

    /**
     * 不能用@Body，@Multipart必须写
     * 修改用户信息并上传头像
     */
    @Multipart
    @POST("user/updateHerd")
    Observable<Result<Object>> updateHerd(@Query("id") String id,
                                          @Query("username") String username,
                                          @Query("password") String password,
                                          @Query("phone") String phone,
                                          @Part MultipartBody.Part file);

    /**
     * 不能用@Body，@Multipart必须写
     * 修改用户信息并上传头像
     */
    @Multipart
    @POST("user/addApk")
    Observable<Result<Object>> addApk(@Query("apk_version") String apk_version,
                                      @Part MultipartBody.Part apk_file);


    /**
     * 不能用@Body，@Multipart必须写
     * 修改用户信息并上传头像
     */
    @Multipart
    @POST("user/addLog")
    Observable<Result<Object>> addLog(@Query("log_time") String log_time,
                                      @Part MultipartBody.Part log_file);

    @POST(NetParmet.LOGIN_PASSWORD)
    Observable<Result<Object>> PostPassWordLogin(
            @Query("phone") String phone,
            @Query("password") String password);

}
