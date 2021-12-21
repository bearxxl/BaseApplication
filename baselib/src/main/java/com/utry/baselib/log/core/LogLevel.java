package com.utry.baselib.log.core;


import com.utry.baselib.log.core.annotation.LogIntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

public class LogLevel {

    public static final int TYPE_VERBOSE = 0;
    public static final int TYPE_DEBUG = 1;
    public static final int TYPE_INFO = 2;
    public static final int TYPE_WARN = 3;
    public static final int TYPE_ERROR = 4;
    public static final int TYPE_WTF = 5;
    public static final int TYPE_ASSERT = 6;

    //用于打印的TAG
    public static final List<String> TYPE_LEVEVLS_ENTRY = new ArrayList<String>() {
        {
            add("");
            add("TYPE_VERBOSE");
            add("TYPE_DEBUG");
            add("TYPE_INFO");
            add("TYPE_WARN");
            add("TYPE_ERROR");
            add("TYPE_WTF");
            add("TYPE_ASSERT");
        }
    };

    public static final List<String> TYPE_LEVELS_VALUE = new ArrayList<String>() {
        {
            add("V");
            add("D");
            add("I");
            add("W");
            add("E");
            add("T");
            add("A");
        }
    };

    @LogIntDef({TYPE_VERBOSE, TYPE_DEBUG, TYPE_INFO, TYPE_WARN, TYPE_ERROR, TYPE_WTF, TYPE_ASSERT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LogLevelType {
    }

}
