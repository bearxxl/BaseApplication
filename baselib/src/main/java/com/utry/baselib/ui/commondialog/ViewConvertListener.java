package com.utry.baselib.ui.commondialog;

import java.io.Serializable;
/**
 * Title: ViewConvertListener.java
 * Description:
 * Copyright (c) 小远机器人版权所有 2021
 * Created DateTime: 2021/10/14 15:10
 * Created by xueli.
 */
public interface ViewConvertListener extends Serializable {
    long serialVersionUID = System.currentTimeMillis();

    void convertView(ViewHolder holder, BaseDialog dialog);
}
