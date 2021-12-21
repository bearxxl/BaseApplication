package com.utry.baselib.baseui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import com.utry.baselib.app.AppManager;


/**
 * Title: BaseActivity.java
 * Description:  基类Activity
 * Copyright (c) 小远机器人版权所有 2021
 * Created DateTime: 2021/8/20 16:50
 * Created by xueli.
 */
public abstract class BaseViewBindingActivity<T extends ViewBinding> extends AppCompatActivity {
    private T _binding;

    protected T getBinding() {
        return _binding;
    }

    @Override
    final protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ExceptionHandler crashHandler = ExceptionHandler.getInstance();
//        //注册crashHandler
//        crashHandler.init(this);
        _binding = getViewBinding();
        setContentView(_binding.getRoot());
        AppManager.getAppManager().addActivity(this);
//        setContentView(R.layout.activity_base);
//        FrameLayout contentView = (FrameLayout) findViewById(R.id.content_view);
//        contentView.addView(getLayoutInflater().inflate(getLayoutId(), contentView, false));

        initView();
        initData();
    }

    protected abstract T getViewBinding();



    protected void initView() {
    }

    protected void initData() {
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }



}
