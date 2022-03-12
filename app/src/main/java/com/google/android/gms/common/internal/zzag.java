package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;

/* loaded from: classes.dex */
public abstract class zzag {
    private static final Object zzfxo = new Object();
    private static zzag zzfxp;

    public static zzag zzcl(Context context) {
        synchronized (zzfxo) {
            if (zzfxp == null) {
                zzfxp = new zzai(context.getApplicationContext());
            }
        }
        return zzfxp;
    }

    public final void zza(String str, String str2, int i, ServiceConnection serviceConnection, String str3) {
        zzb(new zzah(str, str2, i), serviceConnection, str3);
    }

    public final boolean zza(ComponentName componentName, ServiceConnection serviceConnection, String str) {
        return zza(new zzah(componentName, 129), serviceConnection, str);
    }

    public abstract boolean zza(zzah zzahVar, ServiceConnection serviceConnection, String str);

    public final void zzb(ComponentName componentName, ServiceConnection serviceConnection, String str) {
        zzb(new zzah(componentName, 129), serviceConnection, str);
    }

    protected abstract void zzb(zzah zzahVar, ServiceConnection serviceConnection, String str);
}
