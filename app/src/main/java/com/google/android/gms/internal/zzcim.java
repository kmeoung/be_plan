package com.google.android.gms.internal;

import com.google.android.gms.measurement.AppMeasurement;

/* loaded from: classes.dex */
public final class zzcim implements Runnable {
    private /* synthetic */ zzcik zzjeh;
    private /* synthetic */ AppMeasurement.ConditionalUserProperty zzjei;

    public zzcim(zzcik zzcikVar, AppMeasurement.ConditionalUserProperty conditionalUserProperty) {
        this.zzjeh = zzcikVar;
        this.zzjei = conditionalUserProperty;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzjeh.zzb(this.zzjei);
    }
}
