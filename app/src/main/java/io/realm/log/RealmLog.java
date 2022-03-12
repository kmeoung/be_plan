package io.realm.log;

import android.util.Log;
import java.util.Locale;

/* loaded from: classes.dex */
public final class RealmLog {
    private static String REALM_JAVA_TAG = "REALM_JAVA";

    private static native void nativeAddLogger(RealmLogger realmLogger);

    private static native void nativeClearLoggers();

    static native void nativeCloseCoreLoggerBridge(long j);

    static native long nativeCreateCoreLoggerBridge(String str);

    private static native int nativeGetLogLevel();

    private static native void nativeLog(int i, String str, Throwable th, String str2);

    static native void nativeLogToCoreLoggerBridge(long j, int i, String str);

    private static native void nativeRegisterDefaultLogger();

    private static native void nativeRemoveLogger(RealmLogger realmLogger);

    private static native void nativeSetLogLevel(int i);

    public static void add(RealmLogger realmLogger) {
        if (realmLogger == null) {
            throw new IllegalArgumentException("A non-null logger has to be provided");
        }
        nativeAddLogger(realmLogger);
    }

    public static void setLevel(int i) {
        nativeSetLogLevel(i);
    }

    public static int getLevel() {
        return nativeGetLogLevel();
    }

    public static boolean remove(RealmLogger realmLogger) {
        if (realmLogger == null) {
            throw new IllegalArgumentException("A non-null logger has to be provided");
        }
        nativeRemoveLogger(realmLogger);
        return true;
    }

    public static void clear() {
        nativeClearLoggers();
    }

    public static void registerDefaultLogger() {
        nativeRegisterDefaultLogger();
    }

    public static void trace(Throwable th) {
        trace(th, null, new Object[0]);
    }

    public static void trace(String str, Object... objArr) {
        trace(null, str, objArr);
    }

    public static void trace(Throwable th, String str, Object... objArr) {
        log(2, th, str, objArr);
    }

    public static void debug(Throwable th) {
        debug(th, null, new Object[0]);
    }

    public static void debug(String str, Object... objArr) {
        debug(null, str, objArr);
    }

    public static void debug(Throwable th, String str, Object... objArr) {
        log(3, th, str, objArr);
    }

    public static void info(Throwable th) {
        info(th, null, new Object[0]);
    }

    public static void info(String str, Object... objArr) {
        info(null, str, objArr);
    }

    public static void info(Throwable th, String str, Object... objArr) {
        log(4, th, str, objArr);
    }

    public static void warn(Throwable th) {
        warn(th, null, new Object[0]);
    }

    public static void warn(String str, Object... objArr) {
        warn(null, str, objArr);
    }

    public static void warn(Throwable th, String str, Object... objArr) {
        log(5, th, str, objArr);
    }

    public static void error(Throwable th) {
        error(th, null, new Object[0]);
    }

    public static void error(String str, Object... objArr) {
        error(null, str, objArr);
    }

    public static void error(Throwable th, String str, Object... objArr) {
        log(6, th, str, objArr);
    }

    public static void fatal(Throwable th) {
        fatal(th, null, new Object[0]);
    }

    public static void fatal(String str, Object... objArr) {
        fatal(null, str, objArr);
    }

    public static void fatal(Throwable th, String str, Object... objArr) {
        log(7, th, str, objArr);
    }

    private static void log(int i, Throwable th, String str, Object... objArr) {
        if (i >= getLevel()) {
            StringBuilder sb = new StringBuilder();
            if (objArr != null && objArr.length > 0) {
                str = String.format(Locale.US, str, objArr);
            }
            if (th != null) {
                sb.append(Log.getStackTraceString(th));
            }
            if (str != null) {
                if (th != null) {
                    sb.append("\n");
                }
                sb.append(str);
            }
            nativeLog(i, REALM_JAVA_TAG, th, sb.toString());
        }
    }
}
