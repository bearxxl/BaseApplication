package com.utry.baselib.log.core.parser;


import com.utry.baselib.log.core.LogParser;
import com.utry.baselib.log.core.utils.LogObjectUtil;

import java.util.Map;
import java.util.Set;

public class LogMapParse implements LogParser<Map> {
    @Override
    public Class<Map> parseClassType() {
        return Map.class;
    }

    @Override
    public String parseString(Map map) {
        String msg = map.getClass().getName() + " [" + LINE_SEPARATOR;
        Set<Object> keys = map.keySet();
        for (Object key : keys) {
            String itemString = "%s -> %s" + LINE_SEPARATOR;
            Object value = map.get(key);
            if (value != null) {
                if (value instanceof String) {
                    value = "\"" + value + "\"";
                } else if (value instanceof Character) {
                    value = "\'" + value + "\'";
                }
            }
            msg += String.format(itemString, LogObjectUtil.objectToString(key),
                    LogObjectUtil.objectToString(value));
        }
        return msg + "]";
    }
}
