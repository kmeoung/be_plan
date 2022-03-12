package com.google.android.gms.common.internal;

import com.google.android.gms.internal.zzbej;

/* loaded from: classes.dex */
public abstract class DowngradeableSafeParcel extends zzbej implements ReflectedParcelable {
    private static final Object zzfwt = new Object();
    private static ClassLoader zzfwu;
    private static Integer zzfwv;
    private boolean zzfww = false;

    private static ClassLoader zzakt() {
        synchronized (zzfwt) {
        }
        return null;
    }

    protected static Integer zzaku() {
        synchronized (zzfwt) {
        }
        return null;
    }

    protected static boolean zzgc(String str) {
        zzakt();
        return true;
    }
}
