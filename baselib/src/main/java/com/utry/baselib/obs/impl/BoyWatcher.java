package com.utry.baselib.obs.impl;


import com.utry.baselib.obs.Watcher;

/**
 * Title: Boy$
 * Description:
 * Copyright (c) 版权所有 2021
 * Created DateTime: 2021/12/21$ 11:10$
 * Created by xueli$.
 */
public class BoyWatcher implements Watcher {

    private String name;//名字

    public BoyWatcher(String name) {
        this.name = name;
    }

    @Override
    public void update(Object message) {//男孩的具体反应
        System.out.println(name + ",收到了信息:" + message + "屁颠颠的去取快递.");
    }
}
