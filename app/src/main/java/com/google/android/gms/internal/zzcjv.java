package com.google.android.gms.internal;

import android.content.ComponentName;

/* loaded from: classes.dex */
final class zzcjv implements Runnable {
    private /* synthetic */ zzcjr zzjfy;

    public zzcjv(zzcjr zzcjrVar) {
        this.zzjfy = zzcjrVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzjfy.zzjfo.onServiceDisconnected(new ComponentName(this.zzjfy.zzjfo.getContext(), "com.google.android.gms.measurement.AppMeasurementService"));
    }
}
