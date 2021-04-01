package com.example.android_demo.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.android_demo.repository.Network;
import com.example.android_demo.repository.Scheduler;
import com.example.android_demo.repository.SingleLiveData;
import com.example.android_demo.repository.Subs;
import com.example.android_demo.repository.entity.submit.SLogin;

/**
 * Created by: var_rain.
 * Created date: 2020/7/7.
 * Description: 登录界面
 */
public class LoginModel extends AndroidViewModel {

    public MutableLiveData<String> account;
    public MutableLiveData<String> password;

    private SingleLiveData<Object> login;

    public LoginModel(@NonNull Application application) {
        super(application);
        this.account = new MutableLiveData<>();
        this.password = new MutableLiveData<>();

        this.login = new SingleLiveData<>();
    }

    /**
     * 登录
     */
    public SingleLiveData<Object> login() {
        SLogin body = new SLogin();
        body.setUsername("");
        body.setPassword("");
        Network.api().login(body)
                .compose(Scheduler.apply())
                .subscribe(new Subs<Object>() {
                    @Override
                    public void onSuccess(Object data) {
                        login.setValue(data);
                    }
                });
        return this.login;
    }
}
