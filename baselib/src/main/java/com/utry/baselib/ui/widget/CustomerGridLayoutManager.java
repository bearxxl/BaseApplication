package com.utry.baselib.ui.widget;

import android.content.Context;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Title: CustomerGridLayoutManager$
 * Description:
 * Copyright (c) 小远机器人版权所有 2021
 * Created DateTime: 2021/11/4$ 15:45$
 * Created by xueli$.
 */
public class CustomerGridLayoutManager extends GridLayoutManager {
    public CustomerGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);

    }
}
