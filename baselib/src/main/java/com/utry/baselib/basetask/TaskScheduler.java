package com.utry.baselib.basetask;


import android.util.Log;

/**
 * Title: TaskScheduler.java
 * Description:调度器
 * Copyright (c) 版权所有
 * Created DateTime: 2021/12/21 17:32
 * Created by xueli.
 */
public class TaskScheduler {
    private final String TAG = "xxlog";

    public BlockTaskQueue getmTaskQueue() {
        return mTaskQueue;
    }

    public void setmTaskQueue(BlockTaskQueue mTaskQueue) {
        this.mTaskQueue = mTaskQueue;
    }

    private BlockTaskQueue mTaskQueue = BlockTaskQueue.getInstance();
    private ShowTaskExecutor mExecutor;


    private static class ShowDurationHolder {
        private final static TaskScheduler INSTANCE = new TaskScheduler();
    }

    private TaskScheduler() {
        initExecutor();
    }

    private void initExecutor() {
        mExecutor = new ShowTaskExecutor(mTaskQueue);
        mExecutor.start();
    }

    public static TaskScheduler getInstance() {
        return ShowDurationHolder.INSTANCE;
    }

    public void enqueue(ITask task) {
        //因为TaskScheduler这里写成单例，如果isRunning改成false的话，不判断一下，就会一直都是false
        if (!mExecutor.isRunning()) {
            mExecutor.startRunning();
        }
        //按照优先级插入队列 依次播放
        Log.i(TAG, "" + "enqueue" + mTaskQueue.size());
        mTaskQueue.add(task);
    }

    public void resetExecutor() {
        mExecutor.resetExecutor();
    }

    public void clearExecutor() {
        mExecutor.clearExecutor();
    }


}

