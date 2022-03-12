package com.google.android.gms.internal;

import android.os.RemoteException;

/* loaded from: classes.dex */
public final class zzbzr extends zzbzn<Long> {
    public zzbzr(int i, String str, Long l) {
        super(0, str, l);
    }

    /* renamed from: zzd */
    public final Long zza(zzbzv zzbzvVar) {
        try {
            return Long.valueOf(zzbzvVar.getLongFlagValue(getKey(), zzip().longValue(), getSource()));
        } catch (RemoteException unused) {
            return zzip();
        }
    }
}
