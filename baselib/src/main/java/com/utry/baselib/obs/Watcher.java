package com.utry.baselib.obs;

/**
 * Title: ObserVer$
 * Description: 抽象观察者
 * Copyright (c) 版权所有 2021
 * Created DateTime: 2021/12/21$ 11:04$
 * Created by xueli$.
 */
public interface Watcher {
    public void update(Object message);//更新方法
}
