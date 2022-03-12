package com.google.android.gms.internal;

import com.google.android.gms.measurement.AppMeasurement;

/* loaded from: classes.dex */
public final class zzcjb implements Runnable {
    private /* synthetic */ zzciz zzjfe;
    private /* synthetic */ zzcjc zzjff;

    public zzcjb(zzciz zzcizVar, zzcjc zzcjcVar) {
        this.zzjfe = zzcizVar;
        this.zzjff = zzcjcVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzjfe.zza(this.zzjff);
        this.zzjfe.zzjes = null;
        this.zzjfe.zzawd().zza((AppMeasurement.zzb) null);
    }
}
