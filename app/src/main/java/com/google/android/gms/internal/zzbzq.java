package com.google.android.gms.internal;

import android.os.RemoteException;

/* loaded from: classes.dex */
public final class zzbzq extends zzbzn<Integer> {
    public zzbzq(int i, String str, Integer num) {
        super(0, str, num);
    }

    /* renamed from: zzc */
    public final Integer zza(zzbzv zzbzvVar) {
        try {
            return Integer.valueOf(zzbzvVar.getIntFlagValue(getKey(), zzip().intValue(), getSource()));
        } catch (RemoteException unused) {
            return zzip();
        }
    }
}
