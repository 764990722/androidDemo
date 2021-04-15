package com.example.android_demo;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
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
import com.example.android_demo.utils.TimeUtils;
import com.example.android_demo.utils.Toast;
import com.example.android_demo.utils.Utils;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.zhihu.matisse.internal.utils.PathUtils.getPath;


/**
 * Created by: PeaceJay
 * Created date: 2020/11/12.
 * Description: 首页
 */
public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int READ_REQUEST_CODE = 42;

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
        String editName = MCache.get("editName", "admin");
        String editPassword = MCache.get("editPassword", "a123456");
        binding.editName.setText(editName);
        binding.editPassword.setText(editPassword);
    }

    /**
     * 登录
     */
    public void sureView() {
        if (TextUtils.isEmpty(binding.editName.getText().toString().trim())) {
            Toast.show("请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(binding.editPassword.getText().toString().trim())) {
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
        startActivityForResult(new Intent(this, RegisterActivity.class), 1001);
    }

    /**
     * 用户列表
     */
    public void userList() {
        startActivity(new Intent(MainActivity.this, UserListActivity.class));
    }


    /**
     * APK文件上传
     */
    public void apkFile() {
        requestPermission(Permission.Group.STORAGE, Permission.Group.CAMERA);
    }

    private void requestPermission(String[] STORAGE, String... CAMERA) {
        AndPermission.with(this)
                .runtime()
                .permission(STORAGE, CAMERA)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        //成功
                        performFileSearch();
                    }
                })
                .start();
    }

    public void performFileSearch() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == READ_REQUEST_CODE) {
                Uri uri = null;
                if (data != null) {
                    uri = data.getData();
                    Log.i(TAG, "sPath1: " + uri.toString());
                    String sPath1 = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        // Paul Burke写的函数，根据Uri获得文件路径
                        sPath1 = getPath(this, uri);
                    }
                    assert sPath1 != null;
                    Log.i(TAG, "sPath2: " + sPath1);
                    File file = new File(sPath1);
                    RequestBody requestBody = RequestBody.create(file, MediaType.parse("multipart/form-data"));
                    MultipartBody.Part part = MultipartBody.Part.createFormData("apk_file", file.getName(), requestBody);
                    Network.api().addApk(Utils.getVersionName(App.ins()),
                            part)
                            .compose(Scheduler.apply())
                            .subscribe(new Subs<Object>() {
                                @Override
                                public void onSuccess(Object data) {
                                    Toast.show("上传成功");
                                }
                            });


//                    RequestBody requestBody = RequestBody.create(file, MediaType.parse("multipart/form-data"));
//                    MultipartBody.Part part = MultipartBody.Part.createFormData("log_file", file.getName(), requestBody);
//                    Network.api().addLog(TimeUtils.getCurrentTime(TimeUtils.Format_TIME8),
//                            part)
//                            .compose(Scheduler.apply())
//                            .subscribe(new Subs<Object>() {
//                                @Override
//                                public void onSuccess(Object data) {
//                                    Toast.show("上传成功");
//                                }
//                            });

                }
            }
        }


    }


}