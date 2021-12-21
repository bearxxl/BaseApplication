package com.utry.baselib.baseui;

import android.util.SparseArray;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Title: BaseViewHolder$
 * Description:万能ViewHolder
 * Copyright (c) 小远机器人版权所有 2021
 * Created DateTime: 2021/6/1$ 16:54$
 * Created by xueli$.
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {
    // SparseArray 比 HashMap 更省内存，在某些条件下性能更好，只能存储 key 为 int 类型的数据，
    // 用来存放 View 以减少 findViewById 的次数
    private SparseArray<View> viewSparseArray;

    //这个是item的对象
    private View mItemView;

    public BaseViewHolder(View itemView) {
        super(itemView);
        this.mItemView = itemView;
        viewSparseArray = new SparseArray<>();
    }

    /**
     * 根据 ID 来获取 View
     * @param viewId viewID
     * @param <T>    泛型
     * @return 将结果强转为 View 或 View 的子类型
     */
    public <T extends View> T getView(int viewId) {
        // 先从缓存中找，找打的话则直接返回
        // 如果找不到则 findViewById ，再把结果存入缓存中
        View view = viewSparseArray.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            viewSparseArray.put(viewId, view);
        }
        return (T) view;
    }

    //获取item的对象
    public View getItemView() {
        return mItemView;
    }
}

