package eu.davidea.flexibleadapter.utils;

import android.util.Log;

/* loaded from: classes.dex */
public class Logger {
    private String instanceTag;

    public Logger(String str) {
        this.instanceTag = str;
        Log.useTag(null);
    }

    public void v(String str, Object... objArr) {
        if (Log.isVerboseEnabled()) {
            Log.v(this.instanceTag, Log.formatMessage(str, objArr));
        }
    }

    public void d(String str, Object... objArr) {
        if (Log.isDebugEnabled()) {
            Log.d(this.instanceTag, Log.formatMessage(str, objArr));
        }
    }

    public void i(String str, Object... objArr) {
        if (Log.isInfoEnabled()) {
            Log.i(this.instanceTag, Log.formatMessage(str, objArr));
        }
    }

    public void w(String str, Object... objArr) {
        if (Log.isWarnEnabled()) {
            Log.w(this.instanceTag, Log.formatMessage(str, objArr));
        }
    }

    public void w(Throwable th, String str, Object... objArr) {
        if (Log.isWarnEnabled()) {
            Log.w(this.instanceTag, Log.formatMessage(str, objArr), th);
        }
    }

    public void e(String str, Object... objArr) {
        if (Log.isErrorEnabled()) {
            Log.e(this.instanceTag, Log.formatMessage(str, objArr));
        }
    }

    public void e(Throwable th, String str, Object... objArr) {
        if (Log.isErrorEnabled()) {
            Log.e(this.instanceTag, Log.formatMessage(str, objArr), th);
        }
    }

    public void wtf(String str, Object... objArr) {
        if (Log.isErrorEnabled()) {
            Log.wtf(this.instanceTag, Log.formatMessage(str, objArr));
        }
    }

    public void wtf(Throwable th, String str, Object... objArr) {
        if (Log.isErrorEnabled()) {
            Log.wtf(this.instanceTag, Log.formatMessage(str, objArr), th);
        }
    }
}
