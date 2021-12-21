package com.utry.baselib.basetask;

import java.lang.ref.WeakReference;

/**
 * Title: TaskEvent.java
 * Description:任务实体
 * Copyright (c) 版权所有
 * Created DateTime: 2021/12/21 17:32
 * Created by xueli.
 */
public class TaskEvent {
    private WeakReference<ITask> mTask;
    int mEventType;

    public ITask getTask() {
        return mTask.get();
    }

    public void setTask(ITask mTask) {
        this.mTask = new WeakReference<>(mTask);
    }

    public int getEventType() {
        return mEventType;
    }

    public void setEventType(int mEventType) {
        this.mEventType = mEventType;
    }

    public static class EventType {
        public static final int DO = 0X00;
        public static final int FINISH = 0X01;
    }
}

