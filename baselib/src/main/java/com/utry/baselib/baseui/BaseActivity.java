package com.utry.baselib.baseui;

import android.app.Activity;
import android.content.pm.PackageManager;
import

        android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.utry.baselib.R;
import com.utry.baselib.app.AppManager;


/**
 * Title: BaseActivity.java
 * Description:
 * Copyright (c) 小远机器人版权所有 2021
 * Created DateTime: 2021/8/20 16:50
 * Created by xueli.
 */
public abstract class BaseActivity extends AppCompatActivity {


    @Override
    final protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ExceptionHandler crashHandler = ExceptionHandler.getInstance();
//        //注册crashHandler
//        crashHandler.init(this);

        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.activity_base);
        FrameLayout contentView = (FrameLayout) findViewById(R.id.content_view);
        contentView.addView(getLayoutInflater().inflate(getLayoutId(), contentView, false));
        initView();
        initData();
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 0x01;

    protected abstract void afterPermissions();

    protected abstract String[] needPermissions();

    public void verifyStoragePermissions(Activity activity) {
        try {
            //检测权限
            if (!checkPermissions(needPermissions())) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, needPermissions(), REQUEST_EXTERNAL_STORAGE);
            } else {
                afterPermissions();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkPermissions(String[] neededPermissions) {
        if (neededPermissions == null || neededPermissions.length == 0) {
            return true;
        }
        boolean allGranted = true;
        for (String neededPermission : neededPermissions) {
            allGranted &= ContextCompat.checkSelfPermission(this.getApplicationContext(), neededPermission) == PackageManager.PERMISSION_GRANTED;
        }
        return allGranted;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean allGranted = true;
        for (int results : grantResults) {
            allGranted &= results == PackageManager.PERMISSION_GRANTED;
        }
        if (allGranted) {
            afterPermissions();
        } else {
            Toast.makeText(this, "Maybe can not cache the video or play the local resource!", Toast.LENGTH_LONG).show();
        }
    }

    protected void initView() {
    }


    protected abstract int getLayoutId();

    protected void initData() {
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }


    public boolean hasBack() {
        return false;
    }

}
