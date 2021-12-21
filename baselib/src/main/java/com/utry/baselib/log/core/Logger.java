package com.utry.baselib.log.core;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;


import androidx.annotation.RequiresApi;

import com.utry.baselib.log.ProfUseLogUtils;
import com.utry.baselib.log.core.utils.LogCommonUtil;
import com.utry.baselib.log.core.utils.LogObjectUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Locale;

import static com.utry.baselib.log.core.LogLevel.TYPE_DEBUG;
import static com.utry.baselib.log.core.LogLevel.TYPE_ERROR;
import static com.utry.baselib.log.core.LogLevel.TYPE_INFO;
import static com.utry.baselib.log.core.LogLevel.TYPE_VERBOSE;
import static com.utry.baselib.log.core.LogLevel.TYPE_WARN;
import static com.utry.baselib.log.core.LogLevel.TYPE_WTF;


public final class Logger implements LogPrinter {

    private static final String TAG = Logger.class.getName();

    private LogConfigImpl mLogConfig;

    public Logger() {
        mLogConfig = LogConfigImpl.getInstance();
        mLogConfig.addParserClass(LogConstants.DEFAULT_PARSE_CLASS);
    }

    /**
     * 打印字符串
     * @param type
     * @param msg
     * @param args
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void logString(@LogLevel.LogLevelType int type, String tag, String msg, Object... args) {

        if (null != args && args.length > 0) {
            msg += getObjectsString(args);
        }

        //ProfUseLogUtils.i(TAG, "打印字符串 type===" + LogLevel.TYPE_LEVEVLS_ENTRY[type] + " &msg.length===" + msg.length());

        logString(type, tag, msg, false);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void logString(@LogLevel.LogLevelType int type, String tag, String msg, boolean isPart) {

        if (!mLogConfig.isEnable()) {
            return;
        }
        if (type < mLogConfig.getLogLevel()) {
            return;
        }

        String tagTemp;
        if (mLogConfig.isUseDefaultTag() || null == tag || tag.length() == 0) {
            tagTemp = generateTag(type);
        } else {
            tagTemp = tag;
        }

        if (msg.length() > LogConstants.LINE_MAX) {
            if (mLogConfig.isShowBorder()) {
                printLog(type, tagTemp, LogCommonUtil.printDividingLine(LogCommonUtil.DIVIDER_TOP));
                printLog(type, tagTemp, LogCommonUtil.printDividingLine(LogCommonUtil.DIVIDER_NORMAL) + getTopStackInfo(type));
                printLog(type, tagTemp, LogCommonUtil.printDividingLine(LogCommonUtil.DIVIDER_CENTER));
            } else {
                printLog(type, tagTemp, getTopStackInfo(type));
            }
            for (String subMsg : LogCommonUtil.largeStringToList(msg)) {

                logString(type, tagTemp, subMsg, true);

            }
            if (mLogConfig.isShowBorder()) {
                printLog(type, tagTemp, LogCommonUtil.printDividingLine(LogCommonUtil.DIVIDER_BOTTOM));
            }
            return;
        }

        if (mLogConfig.isShowBorder()) {
            if (isPart) {
                for (String sub : msg.split(LogConstants.BR)) {
                    printLog(type, tagTemp, LogCommonUtil.printDividingLine(LogCommonUtil.DIVIDER_NORMAL) + sub);
                }
            } else {

                printLog(type, tagTemp, LogCommonUtil.printDividingLine(LogCommonUtil.DIVIDER_TOP));
                printLog(type, tagTemp, LogCommonUtil.printDividingLine(LogCommonUtil.DIVIDER_NORMAL) + getTopStackInfo(type));
                printLog(type, tagTemp, LogCommonUtil.printDividingLine(LogCommonUtil.DIVIDER_CENTER));
                for (String sub : msg.split(LogConstants.BR)) {
                    printLog(type, tagTemp, LogCommonUtil.printDividingLine(LogCommonUtil.DIVIDER_NORMAL) + sub);
                }

                printLog(type, tagTemp, LogCommonUtil.printDividingLine(LogCommonUtil.DIVIDER_BOTTOM));

            }
        } else {
            printLog(type, tagTemp, getTopStackInfo(type));
            printLog(type, tagTemp, msg);
        }
    }


    /**
     * 打印对象
     * @param type
     * @param object
     */
    private void logObject(@LogLevel.LogLevelType int type, Object object) {
        logString(type, "", LogObjectUtil.objectToString(object));
    }

    /**
     * 自动生成tag
     * @param type
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private String generateTag(int type) {
        if (!mLogConfig.isShowBorder()) {
            return mLogConfig.getTagPrefix() + "/" + getTopStackInfo(type);
        }
        return mLogConfig.getTagPrefix();
    }

    /**
     * 获取最顶部stack信息
     * @param type
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private String getTopStackInfo(int type) {

        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        int stackOffset = getStackOffset(trace);
        if (stackOffset == -1) {
            return null;
        }

        StackTraceElement caller = trace[stackOffset];
        String stackTrace = caller.toString();
        stackTrace = stackTrace.substring(stackTrace.lastIndexOf('('), stackTrace.length());
        String tag = "%s.%s%s";
        String callerClazzName = caller.getClassName();
        //callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), stackTrace);

        return new SimpleDateFormat("yyyy-MM-dd HH-mm-ss", Locale.getDefault()).format(new Date()) + " "
                + LogLevel.TYPE_LEVEVLS_ENTRY.get(type) + ": " + tag;

    }

    private int getStackOffset(StackTraceElement[] trace) {
        for (int i = LogConstants.MIN_STACK_OFFSET; i < trace.length; i++) {
            StackTraceElement e = trace[i];
            String name = e.getClassName();
            //if (name.equals(ProfUseLogUtils.class.getName()) || name.equals(RobotLoggerImpl.class.getName())) {
            if (name.equals(ProfUseLogUtils.class.getName())) {
                return ++i;
            }
        }
        return -1;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void d(String tag, String message, Object... args) {
        logString(TYPE_DEBUG, tag, message, args);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void d(String message, Object... args) {
        logString(TYPE_DEBUG, "", message, args);
    }

    @Override
    public void d(Object object) {
        logObject(TYPE_DEBUG, object);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void e(String tag, String message, Object... args) {
        logString(TYPE_ERROR, tag, message, args);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void e(String message, Object... args) {
        logString(TYPE_ERROR, "", message, args);
    }

    @Override
    public void e(Object object) {
        logObject(TYPE_ERROR, object);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void w(String tag, String message, Object... args) {
        logString(TYPE_WARN, tag, message, args);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void w(String message, Object... args) {
        logString(TYPE_WARN, "", message, args);
    }

    @Override
    public void w(Object object) {
        logObject(TYPE_WARN, object);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void i(String tag, String message, Object... args) {
        logString(TYPE_INFO, tag, message, args);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void i(String message, Object... args) {
        logString(TYPE_INFO, "", message, args);
    }

    @Override
    public void i(Object object) {
        logObject(TYPE_INFO, object);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void v(String tag, String message, Object... args) {
        logString(TYPE_VERBOSE, tag, message, args);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void v(String message, Object... args) {
        logString(TYPE_VERBOSE, "", message, args);
    }

    @Override
    public void v(Object object) {
        logObject(TYPE_VERBOSE, object);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void wtf(String tag, String message, Object... args) {
        logString(TYPE_WTF, tag, message, args);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void wtf(String message, Object... args) {
        logString(TYPE_WTF, "", message, args);
    }

    @Override
    public void wtf(Object object) {
        logObject(TYPE_WTF, object);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void json(String tag, String msg, Object... json) {
        int indent = 4;
        if (TextUtils.isEmpty(json.toString())) {
            d(tag, "JSON{json is null}");
            return;
        }
        try {

            String jsonTemp = json.toString();

            if (jsonTemp.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(jsonTemp);
                String jsonResult = jsonObject.toString(indent);
                d(tag, msg, jsonResult);
            } else if (jsonTemp.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(jsonTemp);
                String jsonResult = jsonArray.toString(indent);
                d(tag, msg, jsonResult);
            }

        } catch (JSONException e) {
            e(tag, "JSONException", e);
        }
    }

    @Override
    public void json(String json) {
        int indent = 4;
        if (TextUtils.isEmpty(json)) {
            d("JSON{json is null}");
            return;
        }
        try {
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                String msg = jsonObject.toString(indent);
                d(msg);
            } else if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                String msg = jsonArray.toString(indent);
                d(msg);
            }
        } catch (JSONException e) {
            e(e);
        }
    }

    /**
     * 处理多个参数，一一列举
     * @param objects
     * @return
     */
    private static String getObjectsString(Object... objects) {

        if (objects.length > 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\n");
            for (int i = 0; i < objects.length; i++) {
                Object object = objects[i];
                if (object == null) {
                    stringBuilder.append("Object").append("[").append(i).append("]").append(" = ").append("null").append("\n");
                } else {
                    stringBuilder.append("Object").append("[").append(i).append("]").append(" = ").append(LogObjectUtil.objectToString(object)).append("\n");
                }
            }
            return stringBuilder.toString();
        } else {
            Object object = LogObjectUtil.objectToString(objects[0]);
            return object == null ? "" : "\n" + "Object[0] = " + object.toString();
        }
    }

    /**
     * 打印日志
     * @param type
     * @param tag
     * @param msg
     */
    private void printLog(@LogLevel.LogLevelType int type, String tag, String msg) {

        switch (type) {
            case TYPE_VERBOSE:
                Log.v(tag, msg);
                break;
            case TYPE_DEBUG:
                Log.d(tag, msg);
                break;
            case TYPE_INFO:
                Log.i(tag, msg);
                break;
            case TYPE_WARN:
                Log.w(tag, msg);
                break;
            case TYPE_ERROR:
                Log.e(tag, msg);
                break;
            case TYPE_WTF:
                Log.wtf(tag, msg);
                break;
            default:
                break;
        }

        if (mLogConfig.isLogToFile()) {

            if (null == mLogConfig.getDoTagList() || mLogConfig.getDoTagList().size() == 0) {
                LogFile.printFile(tag, mLogConfig.getFilePath(), mLogConfig.getFileName(), "", msg + "\n", mLogConfig.isAppend());
            } else if (mLogConfig.getDoTagList().contains(tag)) {
                LogFile.printFile(tag, mLogConfig.getFilePath(), mLogConfig.getFileName(), "", msg + "\n", mLogConfig.isAppend());
            }

            return;
        }

    }

}
