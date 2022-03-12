package com.google.android.gms.internal;

import android.os.RemoteException;

/* loaded from: classes.dex */
public final class zzbzp extends zzbzn<Boolean> {
    public zzbzp(int i, String str, Boolean bool) {
        super(0, str, bool);
    }

    /* renamed from: zzb */
    public final Boolean zza(zzbzv zzbzvVar) {
        try {
            return Boolean.valueOf(zzbzvVar.getBooleanFlagValue(getKey(), zzip().booleanValue(), getSource()));
        } catch (RemoteException unused) {
            return zzip();
        }
    }
}
