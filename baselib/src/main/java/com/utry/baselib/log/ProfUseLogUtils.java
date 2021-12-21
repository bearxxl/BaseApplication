package com.utry.baselib.log;


import com.utry.baselib.log.core.LogConfig;
import com.utry.baselib.log.core.LogConfigImpl;
import com.utry.baselib.log.core.LogPrinter;
import com.utry.baselib.log.core.Logger;

/**
 * 日志管理器
 */
public final class ProfUseLogUtils {

    private static LogPrinter printer;
    private static LogConfigImpl logConfig;

    static {
        printer = new Logger();
        logConfig = LogConfigImpl.getInstance();
    }

    /**
     * 选项配置
     * @return
     */
    public static LogConfig getLogConfig() {
        return logConfig;
    }

    /**
     * verbose输出
     * @param msg
     * @param args
     */
    public static void v(String tag, String msg, Object... args) {
        printer.v(tag, msg, args);
    }

    public static void v(String msg, Object... args) {
        printer.v(msg, args);
    }

    public static void v(Object object) {
        printer.v(object);
    }


    /**
     * debug输出
     * @param msg
     * @param args
     */
    public static void d(String tag, String msg, Object... args) {
        printer.d(tag, msg, args);
    }

    public static void d(String msg, Object... args) {
        printer.d(msg, args);
    }

    public static void d(Object object) {
        printer.d(object);
    }


    /**
     * info输出
     * @param msg
     * @param args
     */
    public static void i(String tag, String msg, Object... args) {
        printer.i(tag, msg, args);
    }

    public static void i(String msg, Object... args) {
        printer.i(msg, args);
    }

    public static void i(Object object) {
        printer.i(object);
    }

    /**
     * warn输出
     * @param msg
     * @param args
     */
    public static void w(String tag, String msg, Object... args) {
        printer.w(tag, msg, args);
    }

    public static void w(String msg, Object... args) {
        printer.w(msg, args);
    }

    public static void w(Object object) {
        printer.w(object);
    }

    /**
     * error输出
     * @param msg
     * @param args
     */
    public static void e(String tag, String msg, Object... args) {
        printer.e(tag, msg, args);
    }

    public static void e(String msg, Object... args) {
        printer.e(msg, args);
    }

    public static void e(Object object) {
        printer.e(object);
    }

    /**
     * assert输出
     * @param msg
     * @param args
     */
    public static void wtf(String tag, String msg, Object... args) {
        printer.wtf(tag, msg, args);
    }

    public static void wtf(String msg, Object... args) {
        printer.wtf(msg, args);
    }

    public static void wtf(Object object) {
        printer.wtf(object);
    }

    /**
     * 打印json
     * @param json
     */
    public static void json(String tag, String msg, Object... json) {
        printer.json(tag, msg, json);
    }

    public static void json(String json) {
        printer.json(json);
    }

}
