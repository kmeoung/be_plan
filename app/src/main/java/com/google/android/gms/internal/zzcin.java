package com.google.android.gms.internal;

import com.google.android.gms.measurement.AppMeasurement;

/* loaded from: classes.dex */
public final class zzcin implements Runnable {
    private /* synthetic */ zzcik zzjeh;
    private /* synthetic */ AppMeasurement.ConditionalUserProperty zzjei;

    public zzcin(zzcik zzcikVar, AppMeasurement.ConditionalUserProperty conditionalUserProperty) {
        this.zzjeh = zzcikVar;
        this.zzjei = conditionalUserProperty;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzjeh.zzc(this.zzjei);
    }
}
