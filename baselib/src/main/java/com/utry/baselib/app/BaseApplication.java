package com.utry.baselib.app;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.utry.baselib.http.retrofit.RetrofitClient;

/**
 * Title: BaseApplication.java
 * Description:
 * Copyright (c) 小远机器人版权所有 2021
 * Created DateTime: 2021/8/20 17:03
 * Created by xueli.
 */
public class BaseApplication extends Application {
    private static Context context;
    private static Resources resources;
    private RetrofitClient client;
    protected static BaseApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        context = getApplicationContext();
        resources = context.getResources();
        AppInit.init(this);
    }

    public RetrofitClient getClient() {
        return client;
    }

    protected <T> T initRetrofit(String baseUrl, boolean isDebug, Class<T> service) {

        client = new RetrofitClient(baseUrl, isDebug, application);
        return client.create(service);
    }
}
