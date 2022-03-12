package com.google.android.gms.internal;

/* loaded from: classes.dex */
public final class zzcjp implements Runnable {
    private /* synthetic */ zzcff zzjds;
    private /* synthetic */ zzckk zzjdy;
    private /* synthetic */ zzcjd zzjfo;
    private /* synthetic */ boolean zzjfs;

    public zzcjp(zzcjd zzcjdVar, boolean z, zzckk zzckkVar, zzcff zzcffVar) {
        this.zzjfo = zzcjdVar;
        this.zzjfs = z;
        this.zzjdy = zzckkVar;
        this.zzjds = zzcffVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzcgb zzcgbVar;
        zzcgbVar = this.zzjfo.zzjfi;
        if (zzcgbVar == null) {
            this.zzjfo.zzawm().zzayr().log("Discarding data. Failed to set user attribute");
            return;
        }
        this.zzjfo.zza(zzcgbVar, this.zzjfs ? null : this.zzjdy, this.zzjds);
        this.zzjfo.zzxg();
    }
}
