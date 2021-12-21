package com.utry.baselib.log.core;

import java.util.List;

public interface LogConfig {

    LogConfig configAllowLog(boolean allowLog);

    LogConfig configTag(boolean useDefault, String prefix);

    LogConfig configShowBorders(boolean showBorder);

    LogConfig configLevel(@LogLevel.LogLevelType int logLevel);

    LogConfig addParserClass(Class<? extends LogParser>... classes);

    LogConfig logToFile(boolean logToFile, String filePath, String fileName, boolean isAppend, List<String> doTagList);

}
