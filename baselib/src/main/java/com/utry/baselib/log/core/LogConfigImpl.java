package com.utry.baselib.log.core;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class LogConfigImpl implements LogConfig {

    private boolean enable = true;
    private boolean useDefaultTag = false;
    private String tagPrefix = "";
    private boolean showBorder = true;

    @LogLevel.LogLevelType
    private int logLevel = LogLevel.TYPE_VERBOSE;
    private List<LogParser> parseList;

    private boolean logToFile = false;
    private String filePath = "";
    private String fileName = "";
    private boolean isAppend = false;
    private List<String> doTagList = new ArrayList<>(3);


    private static LogConfigImpl singleton;

    private LogConfigImpl() {
        parseList = new ArrayList<>();
    }

    public static LogConfigImpl getInstance() {
        if (singleton == null) {
            synchronized (LogConfigImpl.class) {
                if (singleton == null) {
                    singleton = new LogConfigImpl();
                }
            }
        }
        return singleton;
    }

    @Override
    public LogConfig configAllowLog(boolean allowLog) {
        this.enable = allowLog;
        return this;
    }

    @Override
    public LogConfig configTag(boolean useDefault, String prefix) {
        this.useDefaultTag = useDefault;
        this.tagPrefix = prefix;
        return this;
    }

    @Override
    public LogConfig configShowBorders(boolean showBorder) {
        this.showBorder = showBorder;
        return this;
    }

    @Override
    public LogConfig configLevel(@LogLevel.LogLevelType int logLevel) {
        this.logLevel = logLevel;
        return this;
    }

    @Override
    public LogConfig addParserClass(Class<? extends LogParser>... classes) {
        // TODO: 16/3/12 判断解析类的继承关系
        for (Class<? extends LogParser> cla : classes) {
            try {
                parseList.add(0, cla.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    @Override
    public LogConfig logToFile(boolean logToFile, String filePath, String fileName, boolean isAppend, List<String> doTagList) {
        this.logToFile = logToFile;
        this.filePath = filePath;
        this.fileName = fileName;
        this.isAppend = isAppend;
        this.doTagList = doTagList;
        return this;
    }

    public boolean isEnable() {
        return enable;
    }

    public String getTagPrefix() {
        if (TextUtils.isEmpty(tagPrefix)) {
            return "ProfUseLogUtils-";
        }

        return tagPrefix;
    }


    public boolean isShowBorder() {
        return showBorder;
    }

    public int getLogLevel() {
        return logLevel;
    }

    public List<LogParser> getParseList() {
        return parseList;
    }


    public boolean isUseDefaultTag() {
        return useDefaultTag;
    }

    public boolean isLogToFile() {
        return logToFile;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public boolean isAppend() {
        return isAppend;
    }

    public List<String> getDoTagList() {
        return doTagList;
    }
}
