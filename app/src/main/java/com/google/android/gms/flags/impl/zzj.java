package com.google.android.gms.flags.impl;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.android.gms.internal.zzbzz;

/* loaded from: classes.dex */
public final class zzj {
    private static SharedPreferences zzhgj;

    public static SharedPreferences zzdf(Context context) throws Exception {
        SharedPreferences sharedPreferences;
        synchronized (SharedPreferences.class) {
            if (zzhgj == null) {
                zzhgj = (SharedPreferences) zzbzz.zzb(new zzk(context));
            }
            sharedPreferences = zzhgj;
        }
        return sharedPreferences;
    }
}
