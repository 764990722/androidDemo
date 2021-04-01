package com.example.android_demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by: PeaceJay
 * Created date: 2020/11/12
 * Description: 启动页
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler.sendEmptyMessageDelayed(1, 2000);
    }


    private final Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                finish();
            }
            return false;
        }
    });
}


