package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.internal.zzbgc;

/* loaded from: classes.dex */
public final class zzbf {
    private static Object sLock = new Object();
    private static boolean zzckd;
    private static String zzfyi;
    private static int zzfyj;

    public static String zzcm(Context context) {
        zzco(context);
        return zzfyi;
    }

    public static int zzcn(Context context) {
        zzco(context);
        return zzfyj;
    }

    private static void zzco(Context context) {
        Bundle bundle;
        synchronized (sLock) {
            if (!zzckd) {
                zzckd = true;
                try {
                    bundle = zzbgc.zzcy(context).getApplicationInfo(context.getPackageName(), 128).metaData;
                } catch (PackageManager.NameNotFoundException e) {
                    Log.wtf("MetadataValueReader", "This should never happen.", e);
                }
                if (bundle != null) {
                    zzfyi = bundle.getString("com.google.app.id");
                    zzfyj = bundle.getInt("com.google.android.gms.version");
                }
            }
        }
    }
}
