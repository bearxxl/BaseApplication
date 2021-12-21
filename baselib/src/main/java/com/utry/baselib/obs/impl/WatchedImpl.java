package com.utry.baselib.obs.impl;


import com.utry.baselib.obs.Watched;
import com.utry.baselib.obs.Watcher;

import java.util.ArrayList;
import java.util.List;

/**
 * Title: Postman$
 * Description: 被观察者
 * Copyright (c) 版权所有
 * Created DateTime: 2021/12/21$ 11:07$
 * Created by xueli$.
 */
public class WatchedImpl implements Watched {//快递员

    private List<Watcher> observerList = new ArrayList<Watcher>();//保存收件人（观察者）的信息

    @Override
    public void addObserver(Watcher observer) {
        observerList.add(observer);
    }

    @Override
    public void removeObserver(Watcher observer) {
        observerList.remove(observer);
    }

    @Override
    public void removeAll(Watcher observer) {
        observerList.clear();
    }

    @Override
    public void notifyObserver(Object message) {
        for (Watcher observer : observerList) {
            observer.update(message);
        }
    }
}
