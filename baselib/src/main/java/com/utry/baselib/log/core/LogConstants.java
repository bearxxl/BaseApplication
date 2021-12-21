package com.utry.baselib.log.core;


import com.utry.baselib.log.core.parser.LogBundleParse;
import com.utry.baselib.log.core.parser.LogCollectionParse;
import com.utry.baselib.log.core.parser.LogIntentParse;
import com.utry.baselib.log.core.parser.LogMapParse;
import com.utry.baselib.log.core.parser.LogReferenceParse;
import com.utry.baselib.log.core.parser.LogThrowableParse;

import java.util.List;
import java.util.regex.Pattern;

public class LogConstants {

    public static final String STRING_OBJECT_NULL = "Object[object is null]";

    // 每行最大日志长度-1024 * 3系统Log每行的极限值
    //public static final int LINE_MAX = 1024 * 3;
    public static final int LINE_MAX = 1024 * 3;

    // 解析属性最大层级---超过1时，输出对象过大会内存溢出，慎用
    public static final int MAX_CHILD_LEVEL = 1;

    public static final int MIN_STACK_OFFSET = 5;

    //StringBuilder最大值
    //public static final int MAX_CARCH = 1024 * 5;
    //public static final int MAX_CARCH = 1024 * 20;

    // 换行符
    public static final String BR = System.getProperty("line.separator");

    // 空格
    public static final String SPACE = "\t";

    // 默认支持解析库
    public static final Class<? extends LogParser>[] DEFAULT_PARSE_CLASS = new Class[]{
            LogBundleParse.class, LogIntentParse.class, LogCollectionParse.class,
            LogMapParse.class, LogThrowableParse.class, LogReferenceParse.class
    };


    /**
     * 获取默认解析类
     * @return
     */
    public static final List<LogParser> getParsers() {
        return LogConfigImpl.getInstance().getParseList();
    }

    //是否ROOT，根据系统执行结果判断
    public static boolean IS_ROOT = false;
    public static final String COMMAND_SU = "su";
    public static final String COMMAND_SH = "sh";
    public static final String COMMAND_EXIT = "exit\n";
    public static final String COMMAND_LINE_END = "\n";
    public static final String COMMAND_CONFIRM_ROOT = "echo hello\n";
    public static final int COMMAND_BUFFER_TYPE = 8192;
    public static final Pattern COMMAND_PATTERN_PID = Pattern.compile("\\d+");
    public static final Pattern COMMAND_PATTERN_SPACES = Pattern.compile("\\s+");
    public static final String COMMAND_PS_LOGCAT = "ps logcat";
    public static final String[] COMMAND_LOGCAT_ROOT = {"logcat -v threadtime"};
    public static final String[] COMMAND_LOGCAT = {"logcat"};
    public static final String[] COMMAND_LOGCAT_CLEAR = {"logcat -c"};

    //log在list里的缓存条数
    public static final int LOG_BUFFER_LIMIT = 2048;
    public static final String LOG_KEY_PARAMS_ALERT = "LOG_KEY_PARAMS_ALERT";
    public static final int LOG_ALERT_STATUS_REFRESH = 0x1315;
    public static final int LOG_ALERT_STATUS_CLICK = 0x1316;
    public static final int LOG_ALERT_STATUS_LONG_CLICK = 0x1317;

}
