package com.naver.temy123.baseproject.base.Utils;

/* loaded from: classes.dex */
public class Log {
    private static int STATE = 0;
    private static final int STATE_DEBUG = 0;
    private static final int STATE_RELEASE = 1;

    public static int getSTATE() {
        return STATE;
    }

    public static void setSTATE(int i) {
        STATE = i;
    }

    public static final int d(String str, String str2) {
        if (STATE == 0) {
            return android.util.Log.d(str, str2);
        }
        return -1;
    }

    public static final int i(String str, String str2) {
        if (STATE == 0) {
            return android.util.Log.i(str, str2);
        }
        return -1;
    }

    public static final int e(String str, String str2) {
        if (STATE == 0) {
            return android.util.Log.e(str, str2);
        }
        return -1;
    }
}
