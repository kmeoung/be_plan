package com.nguyenhoanglam.imagepicker.helper;

import android.util.Log;

/* loaded from: classes.dex */
public class LogHelper {
    private static LogHelper INSTANCE = null;
    private static final String TAG = "ImagePicker";
    private boolean isEnable = true;

    private LogHelper() {
    }

    public static LogHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LogHelper();
        }
        return INSTANCE;
    }

    public void setEnable(boolean z) {
        this.isEnable = z;
    }

    public void d(String str) {
        if (this.isEnable) {
            Log.d(TAG, str);
        }
    }

    public void e(String str) {
        if (this.isEnable) {
            Log.e(TAG, str);
        }
    }

    public void w(String str) {
        if (this.isEnable) {
            Log.w(TAG, str);
        }
    }
}
