package com.utry.baselib.basetask;

/**
 * Title: CurrentRunningTask.java
 * Description: 正在执行的任务
 * Copyright (c) 版权所有
 * Created DateTime: 2021/12/21 17:31
 * Created by xueli.
 */
public class CurrentRunningTask {
    private static ITask sCurrentShowingTask;

    public static void setCurrentShowingTask(ITask task) {
        sCurrentShowingTask = task;
    }

    public static void removeCurrentShowingTask() {
        sCurrentShowingTask = null;
    }

    public static ITask getCurrentShowingTask() {
        return sCurrentShowingTask;
    }

    public static boolean getCurrentShowingStatus() {
        return sCurrentShowingTask != null && sCurrentShowingTask.getStatus();
    }
}

