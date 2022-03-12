package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.common.util.zzq;

/* loaded from: classes.dex */
public final class zzbga {
    private static Context zzgck;
    private static Boolean zzgcl;

    public static synchronized boolean zzcw(Context context) {
        boolean z;
        synchronized (zzbga.class) {
            Context applicationContext = context.getApplicationContext();
            if (zzgck == null || zzgcl == null || zzgck != applicationContext) {
                zzgcl = null;
                if (zzq.isAtLeastO()) {
                    z = Boolean.valueOf(applicationContext.getPackageManager().isInstantApp());
                } else {
                    try {
                        context.getClassLoader().loadClass("com.google.android.instantapps.supervisor.InstantAppsRuntime");
                        zzgcl = true;
                    } catch (ClassNotFoundException unused) {
                        z = false;
                    }
                    zzgck = applicationContext;
                    return zzgcl.booleanValue();
                }
                zzgcl = z;
                zzgck = applicationContext;
                return zzgcl.booleanValue();
            }
            return zzgcl.booleanValue();
        }
    }
}
