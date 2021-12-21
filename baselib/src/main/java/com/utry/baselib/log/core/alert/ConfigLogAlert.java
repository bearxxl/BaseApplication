package com.utry.baselib.log.core.alert;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;

import com.utry.baselib.log.core.LogLevel;


/**
 * title:
 * description:
 * @author Administrator
 * @version 1.0
 * @since 2018/11/8
 */
public class ConfigLogAlert implements Parcelable {

    private int logAlertLevel = LogLevel.TYPE_INFO;
    private boolean logAutoFilterTag = false;
    private String[] logFilterTags;

    private int logTextSize = 14;
    private float logTextAlpha = 1;
    private String logBackground = "#66000000";

    private String logLevelColorVerbose = "#BBBBBB";
    private String logLevelColorDebug = "#0B5BBB";
    private String logLevelColorInfo = "#6CDADA";
    private String logLevelColorWarn = "#05FF36";
    private String logLevelColorError = "#FF6B68";
    private String logLevelColorWTF = "#555555";
    private String logLevelColorAssert = "#999999";

    public int getColor(String typeLevelsValue) {
        if (typeLevelsValue.equals(LogLevel.TYPE_LEVELS_VALUE.get(LogLevel.TYPE_VERBOSE))) {
            return Color.parseColor(logLevelColorVerbose);
        } else if (typeLevelsValue.equals(LogLevel.TYPE_LEVELS_VALUE.get(LogLevel.TYPE_DEBUG))) {
            return Color.parseColor(logLevelColorDebug);
        } else if (typeLevelsValue.equals(LogLevel.TYPE_LEVELS_VALUE.get(LogLevel.TYPE_INFO))) {
            return Color.parseColor(logLevelColorInfo);
        } else if (typeLevelsValue.equals(LogLevel.TYPE_LEVELS_VALUE.get(LogLevel.TYPE_WARN))) {
            return Color.parseColor(logLevelColorWarn);
        } else if (typeLevelsValue.equals(LogLevel.TYPE_LEVELS_VALUE.get(LogLevel.TYPE_ERROR))) {
            return Color.parseColor(logLevelColorError);
        } else if (typeLevelsValue.equals(LogLevel.TYPE_LEVELS_VALUE.get(LogLevel.TYPE_WTF))) {
            return Color.parseColor(logLevelColorWTF);
        } else {
            return Color.parseColor(logLevelColorAssert);
        }
    }

    public int getLogAlertLevel() {
        return logAlertLevel;
    }

    public ConfigLogAlert setLogAlertLevel(int logAlertLevel) {
        this.logAlertLevel = logAlertLevel;
        return this;
    }

    public boolean isLogAutoFilterTag() {
        return logAutoFilterTag;
    }

    public ConfigLogAlert setLogAutoFilterTag(boolean logAutoFilterTag) {
        this.logAutoFilterTag = logAutoFilterTag;
        return this;
    }

    public String[] getLogFilterTags() {
        return logFilterTags;
    }

    public ConfigLogAlert setLogFilterTags(String[] logFilterTags) {
        this.logFilterTags = logFilterTags;
        return this;
    }

    public int getLogTextSize() {
        return logTextSize;
    }

    public ConfigLogAlert setLogTextSize(int logTextSize) {
        this.logTextSize = logTextSize;
        return this;
    }

    public float getLogTextAlpha() {
        return logTextAlpha;
    }

    public ConfigLogAlert setLogTextAlpha(float logTextAlpha) {
        this.logTextAlpha = logTextAlpha;
        return this;
    }

    public String getLogBackground() {
        return logBackground;
    }

    public void setLogBackground(String logBackground) {
        this.logBackground = logBackground;
    }

    public String getLogLevelColorVerbose() {
        return logLevelColorVerbose;
    }

    public ConfigLogAlert setLogLevelColorVerbose(String logLevelColorVerbose) {
        this.logLevelColorVerbose = logLevelColorVerbose;
        return this;
    }

    public String getLogLevelColorDebug() {
        return logLevelColorDebug;
    }

    public ConfigLogAlert setLogLevelColorDebug(String logLevelColorDebug) {
        this.logLevelColorDebug = logLevelColorDebug;
        return this;
    }

    public String getLogLevelColorInfo() {
        return logLevelColorInfo;
    }

    public ConfigLogAlert setLogLevelColorInfo(String logLevelColorInfo) {
        this.logLevelColorInfo = logLevelColorInfo;
        return this;
    }

    public String getLogLevelColorWarn() {
        return logLevelColorWarn;
    }

    public ConfigLogAlert setLogLevelColorWarn(String logLevelColorWarn) {
        this.logLevelColorWarn = logLevelColorWarn;
        return this;
    }

    public String getLogLevelColorError() {
        return logLevelColorError;
    }

    public ConfigLogAlert setLogLevelColorError(String logLevelColorError) {
        this.logLevelColorError = logLevelColorError;
        return this;
    }

    public String getLogLevelColorWTF() {
        return logLevelColorWTF;
    }

    public ConfigLogAlert setLogLevelColorWTF(String logLevelColorWTF) {
        this.logLevelColorWTF = logLevelColorWTF;
        return this;
    }

    public String getLogLevelColorAssert() {
        return logLevelColorAssert;
    }

    public ConfigLogAlert setLogLevelColorAssert(String logLevelColorAssert) {
        this.logLevelColorAssert = logLevelColorAssert;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.logAlertLevel);
        dest.writeByte(this.logAutoFilterTag ? (byte) 1 : (byte) 0);
        dest.writeStringArray(this.logFilterTags);
        dest.writeInt(this.logTextSize);
        dest.writeFloat(this.logTextAlpha);
        dest.writeString(this.logBackground);
        dest.writeString(this.logLevelColorVerbose);
        dest.writeString(this.logLevelColorDebug);
        dest.writeString(this.logLevelColorInfo);
        dest.writeString(this.logLevelColorWarn);
        dest.writeString(this.logLevelColorError);
        dest.writeString(this.logLevelColorWTF);
        dest.writeString(this.logLevelColorAssert);
    }

    public ConfigLogAlert() {
    }

    protected ConfigLogAlert(Parcel in) {
        this.logAlertLevel = in.readInt();
        this.logAutoFilterTag = in.readByte() != 0;
        this.logFilterTags = in.createStringArray();
        this.logTextSize = in.readInt();
        this.logTextAlpha = in.readFloat();
        this.logBackground = in.readString();
        this.logLevelColorVerbose = in.readString();
        this.logLevelColorDebug = in.readString();
        this.logLevelColorInfo = in.readString();
        this.logLevelColorWarn = in.readString();
        this.logLevelColorError = in.readString();
        this.logLevelColorWTF = in.readString();
        this.logLevelColorAssert = in.readString();
    }

    public static final Creator<ConfigLogAlert> CREATOR = new Creator<ConfigLogAlert>() {
        @Override
        public ConfigLogAlert createFromParcel(Parcel source) {
            return new ConfigLogAlert(source);
        }

        @Override
        public ConfigLogAlert[] newArray(int size) {
            return new ConfigLogAlert[size];
        }
    };

}
