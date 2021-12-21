package com.utry.baselib.ui.commondialog;


import androidx.annotation.LayoutRes;

/**
 * Title: CommonDialog.java
 * Description:公用样式Dialog
 * Copyright (c) 小远机器人版权所有 2021
 * Created DateTime: 2021/10/14 15:10
 * Created by xueli.
 */
public class CommonDialog extends BaseDialog {
    private ViewConvertListener convertListener;

    public static CommonDialog newInstance() {
        CommonDialog dialog = new CommonDialog();
        return dialog;
    }

    /**
     * 设置Dialog布局
     *
     * @param layoutId
     * @return
     */
    public  CommonDialog setLayoutId(@LayoutRes int layoutId) {
        this.mLayoutResId = layoutId;
        return this;
    }

    @Override
    public int setUpLayoutId() {
        return mLayoutResId;
    }

    @Override
    public void convertView(ViewHolder holder, BaseDialog dialog) {
        if (convertListener != null) {
            convertListener.convertView(holder, dialog);
        }
    }

    public  CommonDialog setConvertListener(ViewConvertListener convertListener) {
        this.convertListener = convertListener;
        return this;
    }
}
