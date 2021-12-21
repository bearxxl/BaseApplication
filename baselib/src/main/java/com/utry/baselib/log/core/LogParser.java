package com.utry.baselib.log.core;

public interface LogParser<T> {

    String LINE_SEPARATOR = LogConstants.BR;

    Class<T> parseClassType();

    String parseString(T t);

}
