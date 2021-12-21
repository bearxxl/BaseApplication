package com.utry.baselib.log.core;

public interface LogPrinter {

    void d(String tag, String message, Object... args);

    void d(String message, Object... args);

    void d(Object object);

    void e(String tag, String message, Object... args);

    void e(String message, Object... args);

    void e(Object object);

    void w(String tag, String message, Object... args);

    void w(String message, Object... args);

    void w(Object object);

    void i(String tag, String message, Object... args);

    void i(String message, Object... args);

    void i(Object object);

    void v(String tag, String message, Object... args);

    void v(String message, Object... args);

    void v(Object object);

    void wtf(String tag, String message, Object... args);

    void wtf(String message, Object... args);

    void wtf(Object object);

    void json(String tag, String msg, Object... json);

    void json(String json);

}
