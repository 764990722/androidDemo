package com.example.android_demo.ui.activity;

import android.text.TextUtils;

import com.example.android_demo.R;
import com.example.android_demo.databinding.ActivityRegisterBinding;
import com.example.android_demo.repository.Network;
import com.example.android_demo.repository.Scheduler;
import com.example.android_demo.repository.Subs;
import com.example.android_demo.repository.entity.submit.SRegister;
import com.example.android_demo.repository.net.MCache;
import com.example.android_demo.ui.base.BaseActivity;
import com.example.android_demo.utils.Toast;


/**
 * Created by: PeaceJay
 * Created date: 2021/03/30.
 * Description: 注册
 */
public class RegisterActivity extends BaseActivity<ActivityRegisterBinding> {

    private static final String TAG = RegisterActivity.class.getSimpleName();

    @Override
    protected int onLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void onObject() {
        binding.setActivity(this);
    }

    @Override
    protected void onView() {

    }

    @Override
    protected void onData() {

    }

    public void sureView() {
        if (TextUtils.isEmpty(binding.editPhone.getText().toString().trim())){
            Toast.show("请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(binding.editName.getText().toString().trim())){
            Toast.show("请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(binding.editPassword.getText().toString().trim())){
            Toast.show("请输入密码");
            return;
        }
        if (TextUtils.isEmpty(binding.editNewPassword.getText().toString().trim())){
            Toast.show("请输入确认密码");
            return;
        }
        if (!binding.editNewPassword.getText().toString().trim().equals(binding.editPassword.getText().toString().trim())){
            Toast.show("密码不一致请重新输入");
            return;
        }

        SRegister body = new SRegister();
        body.setPhone(binding.editPhone.getText().toString().trim());
        body.setUsername(binding.editName.getText().toString().trim());
        body.setPassword(binding.editPassword.getText().toString().trim());
        Network.api().register(body)
                .compose(Scheduler.apply())
                .subscribe(new Subs<Object>() {
                    @Override
                    public void onSuccess(Object data) {
                        Toast.show("注册成功");
                        MCache.put("editName", binding.editName.getText().toString().trim());
                        MCache.put("editPassword", binding.editPassword.getText().toString().trim());
                        finish();
                    }
                });
    }


}