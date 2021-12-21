package com.utry.baselib.log.core.parser;


import com.utry.baselib.log.core.LogParser;
import com.utry.baselib.log.core.utils.LogObjectUtil;

import java.lang.ref.Reference;

public class LogReferenceParse implements LogParser<Reference> {
    @Override
    public Class<Reference> parseClassType() {
        return Reference.class;
    }

    @Override
    public String parseString(Reference reference) {
        Object actual = reference.get();
        StringBuilder builder = new StringBuilder(reference.getClass().getSimpleName() + "<"
                + actual.getClass().getSimpleName() + "> {");
        builder.append("→" + LogObjectUtil.objectToString(actual));
        return builder.toString() + "}";
    }
}
