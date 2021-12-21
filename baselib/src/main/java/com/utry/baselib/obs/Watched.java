package com.utry.baselib.obs;

/**
 * Title: Observable$
 * Description:
 * Copyright (c) 版权所有 2021
 * Created DateTime: 2021/12/21$ 11:05$
 * Created by xueli$.
 */
public interface Watched {
    void addObserver(Watcher observer);//添加观察者

    void removeObserver(Watcher observer);//删除观察者
    void removeAll(Watcher observer);//删除观察者

    void notifyObserver(Object message);//通知观察者
}
