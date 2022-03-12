package com.nguyenhoanglam.imagepicker.helper;

import android.content.Context;

/* loaded from: classes.dex */
public class PreferenceHelper {
    private static final String PREFS_FILE_NAME = "ImagePicker";

    public static void firstTimeAskingPermission(Context context, String str, boolean z) {
        context.getSharedPreferences(PREFS_FILE_NAME, 0).edit().putBoolean(str, z).apply();
    }

    public static boolean isFirstTimeAskingPermission(Context context, String str) {
        return context.getSharedPreferences(PREFS_FILE_NAME, 0).getBoolean(str, true);
    }
}
