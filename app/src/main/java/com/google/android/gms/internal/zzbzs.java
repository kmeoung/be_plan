package com.google.android.gms.internal;

import android.os.RemoteException;

/* loaded from: classes.dex */
public final class zzbzs extends zzbzn<String> {
    public zzbzs(int i, String str, String str2) {
        super(0, str, str2);
    }

    /* renamed from: zze */
    public final String zza(zzbzv zzbzvVar) {
        try {
            return zzbzvVar.getStringFlagValue(getKey(), zzip(), getSource());
        } catch (RemoteException unused) {
            return zzip();
        }
    }
}
