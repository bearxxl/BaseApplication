package com.utry.baselib.http.fastjson;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Converter;

/**
 * Title: FastJsonResponseBodyConverter
 * Description: 请用一句话描述当前类作用
 * Copyright (c) 小远机器人版权所有 2021
 * Created DateTime: 2021/5/11 17:08
 * Created by liangpingyy.
 */
public class FastJsonResponseBodyConverter <T> implements Converter<ResponseBody, T> {
    private final Type type;

    public FastJsonResponseBodyConverter(Type type) {
        this.type = type;
    }

    /*
     * 转换方法
     */
    @Override
    public T convert(ResponseBody value) throws IOException {
        BufferedSource bufferedSource = Okio.buffer(value.source());
        String tempStr = bufferedSource.readUtf8();
        bufferedSource.close();
        return JSON.parseObject(tempStr, type);

    }

}
