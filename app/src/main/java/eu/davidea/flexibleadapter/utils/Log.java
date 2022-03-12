package eu.davidea.flexibleadapter.utils;

import android.support.annotation.Nullable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public class Log {
    private static int LEVEL = 10;
    private static final String SOURCE_FILE = "SourceFile";
    public static String customTag;
    private static boolean withLineNumber;
    private static boolean withMethodName;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface Level {
        public static final int DEBUG = 3;
        public static final int ERROR = 6;
        public static final int INFO = 4;
        public static final int SUPPRESS = 10;
        public static final int VERBOSE = 2;
        public static final int WARN = 5;
    }

    private Log() {
    }

    public static void setLevel(int i) {
        LEVEL = i;
    }

    public static void logMethodName(boolean z, boolean z2) {
        withMethodName = z;
        withLineNumber = z2;
    }

    public static boolean isVerboseEnabled() {
        return LEVEL <= 2;
    }

    public static boolean isDebugEnabled() {
        return LEVEL <= 3;
    }

    public static boolean isInfoEnabled() {
        return LEVEL <= 4;
    }

    public static boolean isWarnEnabled() {
        return LEVEL <= 5;
    }

    public static boolean isErrorEnabled() {
        return LEVEL <= 6;
    }

    public static void v(String str, Object... objArr) {
        if (isVerboseEnabled()) {
            android.util.Log.v(getTag(), formatMessage(str, objArr));
        }
    }

    public static void d(String str, Object... objArr) {
        if (isDebugEnabled()) {
            android.util.Log.d(getTag(), formatMessage(str, objArr));
        }
    }

    public static void i(String str, Object... objArr) {
        if (isInfoEnabled()) {
            android.util.Log.i(getTag(), formatMessage(str, objArr));
        }
    }

    public static void iTag(String str, String str2, Object... objArr) {
        if (isInfoEnabled()) {
            android.util.Log.i(str, formatMessage(str2, objArr));
        }
    }

    public static void w(String str, Object... objArr) {
        if (isWarnEnabled()) {
            android.util.Log.w(getTag(), formatMessage(str, objArr));
        }
    }

    public static void w(Throwable th, String str, Object... objArr) {
        if (isWarnEnabled()) {
            android.util.Log.w(getTag(), formatMessage(str, objArr), th);
        }
    }

    public static void e(String str, Object... objArr) {
        if (isErrorEnabled()) {
            android.util.Log.e(getTag(), formatMessage(str, objArr));
        }
    }

    public static void e(Throwable th, String str, Object... objArr) {
        if (isErrorEnabled()) {
            android.util.Log.e(getTag(), formatMessage(str, objArr), th);
        }
    }

    public static void wtf(String str, Object... objArr) {
        if (isErrorEnabled()) {
            android.util.Log.wtf(getTag(), formatMessage(str, objArr));
        }
    }

    public static void wtf(Throwable th, String str, Object... objArr) {
        if (isErrorEnabled()) {
            android.util.Log.wtf(getTag(), formatMessage(str, objArr), th);
        }
    }

    public static void useTag(@Nullable String str) {
        customTag = str;
    }

    private static String getTag() {
        if (customTag != null) {
            return customTag;
        }
        String fileName = new Throwable().getStackTrace()[2].getFileName();
        return fileName == null ? SOURCE_FILE : fileName.split("[.]")[0];
    }

    public static String formatMessage(String str, Object... objArr) {
        if (objArr == null || objArr.length != 0) {
            str = String.format(str, objArr);
        }
        return createLog(str);
    }

    private static String createLog(String str) {
        if (!withMethodName) {
            return str;
        }
        StackTraceElement stackTraceElement = new Throwable().getStackTrace()[3];
        return withLineNumber ? String.format("[%s:%s] %s", stackTraceElement.getMethodName(), Integer.valueOf(stackTraceElement.getLineNumber()), str) : String.format("[%s] %s", stackTraceElement.getMethodName(), str);
    }
}
