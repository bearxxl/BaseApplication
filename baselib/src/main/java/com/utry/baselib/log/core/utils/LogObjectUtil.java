package com.utry.baselib.log.core.utils;


import com.utry.baselib.log.core.LogConstants;
import com.utry.baselib.log.core.LogParser;

import java.lang.reflect.Field;

import static com.utry.baselib.log.core.LogConstants.BR;


public class LogObjectUtil {

    /**
     * 将对象转化为String
     * @param object
     * @return
     */
    public static String objectToString(Object object) {

        String resultStr;

        try {

            resultStr = objectToString(object, 0);

        } catch (Exception e) {
            resultStr = "日志要输出的对象太大了，超出范围，请精简";
        }

        return resultStr;
    }

    public static String objectToString(Object object, int childLevel) throws Exception {

        if (object == null) {
            return LogConstants.STRING_OBJECT_NULL;
        }
        if (childLevel > LogConstants.MAX_CHILD_LEVEL) {
            return object.toString();
        }

        if (LogConstants.getParsers() != null && LogConstants.getParsers().size() > 0) {
            for (LogParser logParser : LogConstants.getParsers()) {
                if (logParser.parseClassType().isAssignableFrom(object.getClass())) {
                    return logParser.parseString(object);
                }
            }
        }
        if (LogArrayUtil.isArray(object)) {
            return LogArrayUtil.parseArray(object);
        }
        if (object.toString().startsWith(object.getClass().getName() + "@")) {

            StringBuilder builder = new StringBuilder();
            getClassFields(object.getClass(), builder, object, false, childLevel);
            Class superClass = object.getClass().getSuperclass();

            while (!superClass.equals(Object.class)) {

                getClassFields(superClass, builder, object, true, childLevel);
                superClass = superClass.getSuperclass();
            }

            return builder.toString();
        } else {
            // 若对象重写toString()方法默认走toString()
            return object.toString();
        }

    }

    /**
     * 拼接class的字段和值
     * @param cla
     * @param builder
     * @param o           对象
     * @param isSubClass  死否为子class
     * @param childOffset 递归解析属性的层级
     */
    private static void getClassFields(Class cla, StringBuilder builder, Object o, boolean isSubClass,
                                       int childOffset) throws Exception {
        if (cla.equals(Object.class)) {
            return;
        }

        //if (builder.length() > LogConstants.MAX_CARCH) {
        //    builder.append("日志要输出的对象太大了，超出范围，请精简");
        //    return builder.toString();
        //}

        if (isSubClass) {
            builder.append(BR + BR + "=> ");
        }
        //        String breakLine = childOffset == 0 ? BR : "";
        String breakLine = "";
        builder.append(cla.getSimpleName() + " {");
        Field[] fields = cla.getDeclaredFields();
        for (Field field : fields) {

            field.setAccessible(true);
            Object subObject = null;
            try {
                subObject = field.get(o);
            } catch (IllegalAccessException e) {
                subObject = e;
            } finally {
                if (subObject != null) {
                    if (subObject instanceof String) {
                        subObject = "\"" + subObject + "\"";
                    } else if (subObject instanceof Character) {
                        subObject = "\'" + subObject + "\'";
                    }
                    if (childOffset < LogConstants.MAX_CHILD_LEVEL) {
                        subObject = objectToString(subObject, childOffset + 1);
                    }
                }
                String formatString = breakLine + "%s = %s, ";
                builder.append(String.format(formatString, field.getName(),
                        subObject == null ? "null" : subObject.toString()));
            }
        }
        if (builder.toString().endsWith("{")) {
            builder.append("}");
        } else {
            builder.replace(builder.length() - 2, builder.length() - 1, breakLine + "}");
        }
    }

}
