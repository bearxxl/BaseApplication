package com.utry.baselib.http.fastjson;


import com.alibaba.fastjson.JSON;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * Title: FastJsonRequestBodyConverter
 * Description: 请用一句话描述当前类作用
 * Copyright (c) 小远机器人版权所有 2021
 * Created DateTime: 2021/5/11 17:08
 * Created by liangpingyy.
 */

public class FastJsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

    @Override
    public RequestBody convert(T value) throws IOException {
        return RequestBody.create(MEDIA_TYPE, JSON.toJSONBytes(value));
    }
}
