package com.utry.baselib.app;

import android.content.Context;

/**
 * Title: AppInit.java
 * Description:Context管理类
 * Copyright (c) 小远机器人版权所有 2021
 * Created DateTime: 2021/6/15 13:53
 * Created by xueli.
 */
public class AppInit {

    private static Context context;

    public static void init(Context context) {
        AppInit.context = context;
    }

    public static Context getContext() {
        return context;
    }



}
