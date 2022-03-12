package com.google.android.gms.common.util;

import android.content.Context;
import android.os.DropBoxManager;
import android.util.Log;
import com.google.android.gms.common.internal.zzbq;

/* loaded from: classes.dex */
public final class zzf {
    private static final String[] zzgbg = {"android.", "com.android.", "dalvik.", "java.", "javax."};
    private static DropBoxManager zzgbh = null;
    private static boolean zzgbi = false;
    private static int zzgbj = -1;
    private static int zzgbk;

    public static boolean zza(Context context, Throwable th) {
        return zza(context, th, 0);
    }

    private static boolean zza(Context context, Throwable th, int i) {
        try {
            zzbq.checkNotNull(context);
            zzbq.checkNotNull(th);
            return false;
        } catch (Exception e) {
            Log.e("CrashUtils", "Error adding exception to DropBox!", e);
            return false;
        }
    }
}
