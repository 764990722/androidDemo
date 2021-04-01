package com.example.android_demo;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.example.android_demo.databinding.ActivityMainBinding;
import com.example.android_demo.repository.Network;
import com.example.android_demo.repository.Scheduler;
import com.example.android_demo.repository.Subs;
import com.example.android_demo.repository.entity.submit.SLogin;
import com.example.android_demo.repository.net.MCache;
import com.example.android_demo.ui.activity.RegisterActivity;
import com.example.android_demo.ui.activity.UserListActivity;
import com.example.android_demo.ui.base.BaseActivity;
import com.example.android_demo.utils.Base64Utils;
import com.example.android_demo.utils.Text;
import com.example.android_demo.utils.Toast;


/**
 * Created by: PeaceJay
 * Created date: 2020/11/12.
 * Description: 首页
 */
public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected int onLayout() {
        return R.layout.activity_main;
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

    @Override
    protected void onResume() {
        super.onResume();
        String editName = MCache.get("editName","admin");
        String editPassword = MCache.get("editPassword","a123456");
        binding.editName.setText(editName);
        binding.editPassword.setText(editPassword);
    }

    /**
     * 登录
     */
    public void sureView() {
        if (TextUtils.isEmpty(binding.editName.getText().toString().trim())){
            Toast.show("请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(binding.editPassword.getText().toString().trim())){
            Toast.show("请输入密码");
            return;
        }
        MCache.put("editName", binding.editName.getText().toString().trim());
        MCache.put("editPassword", binding.editPassword.getText().toString().trim());
        SLogin body = new SLogin();
        body.setUsername(binding.editName.getText().toString().trim());
        body.setPassword(binding.editPassword.getText().toString().trim());
        Network.api().login(body)
                .compose(Scheduler.apply())
                .subscribe(new Subs<Object>() {
                    @Override
                    public void onSuccess(Object data) {
                        Log.d(TAG, "onSuccess: ");
                        startActivity(new Intent(MainActivity.this, UserListActivity.class));
                    }
                });
    }

    /**
     * 注册
     */
    public void register() {
        startActivityForResult(new Intent(this, RegisterActivity.class),1001);
    }

    /**
     * 用户列表
     */
    public void userList() {
        startActivity(new Intent(MainActivity.this, UserListActivity.class));
    }



}