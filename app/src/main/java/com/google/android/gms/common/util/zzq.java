package com.google.android.gms.common.util;

import android.os.Build;

/* loaded from: classes.dex */
public final class zzq {
    public static boolean isAtLeastN() {
        return Build.VERSION.SDK_INT >= 24;
    }

    public static boolean isAtLeastO() {
        return Build.VERSION.SDK_INT >= 26 || "O".equals(Build.VERSION.CODENAME) || Build.VERSION.CODENAME.startsWith("OMR") || Build.VERSION.CODENAME.startsWith("ODR");
    }

    public static boolean zzalv() {
        return Build.VERSION.SDK_INT >= 15;
    }

    public static boolean zzalw() {
        return Build.VERSION.SDK_INT >= 16;
    }

    public static boolean zzalx() {
        return Build.VERSION.SDK_INT >= 17;
    }

    public static boolean zzaly() {
        return Build.VERSION.SDK_INT >= 18;
    }

    public static boolean zzalz() {
        return Build.VERSION.SDK_INT >= 19;
    }

    public static boolean zzama() {
        return Build.VERSION.SDK_INT >= 20;
    }

    public static boolean zzamb() {
        return Build.VERSION.SDK_INT >= 21;
    }
}
