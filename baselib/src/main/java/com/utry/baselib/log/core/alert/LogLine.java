/*
 * Copyright (C) 2013 readyState Software Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.utry.baselib.log.core.alert;

import android.content.Context;
import android.text.TextUtils;


import com.utry.baselib.log.core.LogLevel;

import java.util.Calendar;
import java.util.List;

public class LogLine {

    private static final int DATE_INDEX = 0;
    private static final int TIME_INDEX = 1;
    private static final int PID_INDEX = 2;
    private static final int TID_INDEX = 3;
    private static final int LEVEL_INDEX = 4;
    private static final int TAG_INDEX = 5;

    private String mRaw;

    private String mDate;
    private String mTime;
    private String mLevel;
    private int mPid;
    private int mTid;
    private String mTag;
    private String mMessage;

    public LogLine() {
    }

    public LogLine(String raw, boolean isRoot) {

        if (isRoot) {
            doParseRoot(raw);
        } else {
            doParse(raw);
        }

    }

    private void doParse(String raw) {
        mRaw = raw;

        //D/MainActivity: 55555555555
        String[] outer = raw.split(": ");
        String[] parts = outer[0].split("/");

        if (parts.length > 0 && parts[0].length() > 2) {
            //11-12 13:29:38.840 22418 22418 V MainActivity: 44444444444
            //超过1位
            doParseRoot(raw);
            return;
        }

        // parse metadata
        mTime = Calendar.getInstance().getTime().toLocaleString();
        mLevel = parts.length > 0 ? parts[0] : "";
        mTag = parts.length > 1 ? parts[1] : "";

        StringBuffer sb = new StringBuffer();
        for (int i = 1; i < outer.length; i++) {
            final String part = outer[i];
            sb.append(part + ((i == outer.length - 1 || part.length() == 0) ? "" : ": "));
        }
        mMessage = sb.toString();

    }

    private void doParseRoot(String raw) {

        mRaw = raw;

        String[] outer = raw.split(": ");
        String[] parts = outer[0].split(" +");

        // parse metadata
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < parts.length; i++) {
            final String part = parts[i];
            switch (i) {
                case DATE_INDEX:
                    mDate = part;
                    break;
                case TIME_INDEX:
                    mTime = part;
                    break;
                case LEVEL_INDEX:
                    mLevel = part;
                    break;
                case TID_INDEX:
                    try {
                        mTid = Integer.parseInt(part);
                    } catch (NumberFormatException e) {
                        mTid = 0;
                    }
                    break;
                case PID_INDEX:
                    try {
                        mPid = Integer.parseInt(part);
                    } catch (NumberFormatException e) {
                        mPid = 0;
                    }
                    break;
                default:
                    if (i >= TAG_INDEX) {
                        sb.append(part + ((i == parts.length - 1 || part.length() == 0) ? "" : " "));
                    }
            }

        }
        mTag = sb.toString();

        // parse message
        sb = new StringBuffer();
        for (int i = 1; i < outer.length; i++) {
            final String part = outer[i];
            sb.append(part + ((i == outer.length - 1 || part.length() == 0) ? "" : ": "));
        }
        mMessage = sb.toString();

    }


    public String getRaw() {
        if (TextUtils.isEmpty(mRaw)) {
            mRaw = "";
        }
        return mRaw;
    }

    public String getDate() {
        if (TextUtils.isEmpty(mDate)) {
            mDate = "";
        }
        return mDate;
    }

    public String getTime() {
        if (TextUtils.isEmpty(mTime)) {
            mTime = "";
        }
        return mTime;
    }

    public String getDateTime() {
        return getDate() + " " + getTime();
    }

    public String getLevel() {
        if (TextUtils.isEmpty(mLevel)) {
            mLevel = "";
        }
        return mLevel;
    }

    public int getPid() {
        return mPid;
    }

    public int getTid() {
        return mTid;
    }

    public String getTag() {
        if (TextUtils.isEmpty(mTag)) {
            mTag = "";
        }
        return mTag;
    }

    public String getTagLevel() {
        return getLevel() + "/" + getTag();
    }

    public String getMessage() {
        if (TextUtils.isEmpty(mMessage)) {
            mMessage = "";
        }
        return mMessage;
    }

    public static String getLevelName(Context context, String code) {
        List<String> codes = LogLevel.TYPE_LEVELS_VALUE;
        for (int i = 0; i < codes.size(); i++) {
            if (codes.get(i).equals(code)) {
                return LogLevel.TYPE_LEVEVLS_ENTRY.get(i + 1);
            }
        }
        return null;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    public void setTime(String mTime) {
        this.mTime = mTime;
    }

    public void setLevel(String mLevel) {
        this.mLevel = mLevel;
    }

    public void setMessage(String mMessage) {
        this.mMessage = mMessage;
    }
}
