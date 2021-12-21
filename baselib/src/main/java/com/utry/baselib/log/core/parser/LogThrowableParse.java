package com.utry.baselib.log.core.parser;

import android.util.Log;

import com.utry.baselib.log.core.LogParser;


public class LogThrowableParse implements LogParser<Throwable> {
    @Override
    public Class<Throwable> parseClassType() {
        return Throwable.class;
    }

    @Override
    public String parseString(Throwable throwable) {
        return Log.getStackTraceString(throwable);
    }
}
